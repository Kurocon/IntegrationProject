package sampca;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;

import gui.CaUI;
import gui.MainUI;
import network.*;
import protocol.*;
import protocol.builders.*;
import protocol.parsers.PacketParser;

/**
 * Main class to start SAMPCA.
 *
 * Created by kevin on 4/7/14.
 */
public class SAMPCA extends Observable /*implements Runnable*/{

    private static final Logger LOGGER = Logger.getLogger(SAMPCA.class.getName());

	public static final String PROGRAM_NAME = "SAMPCA";
    public static final int MAX_PACKET_SIZE                 = 1024;
    public static final int PACKET_SIZE_AFTER_ENCRYPTION    = 1024;
    public static final int GENERAL_HEADER_SIZE             = 24;
    public static final int PUBLIC_CHAT_HEADER_SIZE			= 0;
    public static final int PRIVATE_CHAT_HEADER_SIZE		= 4;
    public static final int MAX_BYTES_PER_FILE_PACKET       = 736; // MAX SIZE - 288

    public static final boolean ENABLE_ENCRYPTION_OF_PACKETS    = false;
    public static final String PUBLIC_CHAT_ROOM_NAME = "Educafé";
    public static final Level GLOBAL_LOGGER_LEVEL = Level.WARNING;

    // normal, badeend
    public static final String TEXTUREPACK = "normal";
    
    private Timer timer;
    private UDPListener listener;
    private UDPSender sender;
    private AckLog ackLog = new AckLog();
    private String ip;
    private int port;
    private String username;
    private String password;
    private NetworkInterface iface;
    private InetAddress iface_addr;
    private User ownUser;
    
    private LinkedList<User> users = null;

    private Security crypto;

    private MulticastSocket socket;
    private InetAddress group;

    private Thread listenerThread;
    private Thread senderThread;

    private CaUI chatGui = null;

    public boolean finished = false;

    public static void main(String[] args){
        //new SAMPCA(5555, "228.133.102.88", "Kurocon", "testpassword");
        new MainUI();
    }

    public SAMPCA(int port, String ip, String username, String password){
        LOGGER.setLevel(SAMPCA.GLOBAL_LOGGER_LEVEL);
        LOGGER.log(Level.INFO, "SAMPCA is starting...");

        this.port = port;
        this.ip = ip;
        this.username = username;
        this.password = password;

        this.users = new LinkedList<>();

        this.crypto = new Security();
        this.crypto.setPassword(this.password);

        // Get wireless interface
        Enumeration<NetworkInterface> interfaces = null;
        try {
            interfaces = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            LOGGER.log(Level.SEVERE, "No network devices found on this computer ["+e.getMessage()+"]");
            System.exit(1);
        }

        boolean not_found = true;
        while(not_found && interfaces.hasMoreElements()){
            NetworkInterface n = interfaces.nextElement();
            if(n.getName().contains("wlan")){
                // We got the WLAN interface!
                LOGGER.log(Level.INFO, "Using wireless interface "+n.getName()+" for communications.");
                this.iface = n;
                not_found = false;
            }
        }

        Enumeration<InetAddress> iface_addrs = this.iface.getInetAddresses();
        InetAddress iface_addr= iface_addrs.nextElement();
        while((iface_addr.getHostAddress().startsWith("fe80:") || iface_addr.getHostAddress().startsWith("2001:")) && iface_addrs.hasMoreElements()){
            this.iface_addr = iface_addrs.nextElement();
        }

        try {
            this.socket = new MulticastSocket(this.port);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Could not open a socket to the rest of the group! ["+e.getMessage()+"]");
            System.exit(1);
        }

        try {
            this.group = InetAddress.getByName(this.ip);
        } catch (UnknownHostException e) {
            LOGGER.log(Level.SEVERE, "Could not determine correct IP address for group! ["+e.getMessage()+"]");
        }

        try {
            this.socket.joinGroup(new InetSocketAddress(this.group, this.port), this.iface);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(this.username.equals(MainUI.LBL_DEF_NICK) && this.iface_addr.getHostName() != null){
        	this.username = this.iface_addr.getHostName().replace(".local", "");
        }
        
        User main_channel = new UDPUser();
        main_channel.setName(SAMPCA.PUBLIC_CHAT_ROOM_NAME);
        main_channel.setIP(this.group);
        main_channel.setPort(this.port);
        main_channel.setHostname("main_room");
        main_channel.setLastSeen(Timestamp.getCurrentTimeAsLong());
        this.addUser(main_channel);

        this.ownUser = new UDPUser();
        this.ownUser.setName(this.username);
        this.ownUser.setIP(this.iface_addr);
        this.ownUser.setPort(this.port);
        this.ownUser.setHostname(this.iface_addr.getHostName());
        this.ownUser.setLastSeen(Timestamp.getCurrentTimeAsLong());
        this.addUser(this.ownUser);

        this.startListener();
        this.startSender();

        LOGGER.log(Level.INFO, "SAMPCA successfully started");

        LOGGER.log(Level.INFO, "Starting Timer.");

        this.timer = new Timer();
        this.timer.scheduleAtFixedRate(new TimeoutTimerTask(this), 0, 8000);

        LOGGER.log(Level.INFO, "Timer started, we are broadcasting.");

        this.openChatGui();
    }

    private void startListener(){
        this.listener = new UDPListener(this, this.socket);
        this.listener.setCrypto(this.crypto);
        this.listenerThread = new Thread(this.listener);
        this.listenerThread.start();
    }

    private void startSender(){
        this.sender = new UDPSender(this, this.socket, this.group, this.port);
        this.sender.setCrypto(this.crypto);
        this.senderThread = new Thread(this.sender);
        this.senderThread.start();
    }

    private void openChatGui(){
        this.chatGui = new CaUI(this);
    }

    public void sendPublicMessage(String msg){
    	ChatBuilder cb = new ChatBuilder();
        cb.setMessage(msg);
        this.sendBuilder(cb, this.group);
    }
    
    public void sendAckMessage(long timestamp, InetAddress destination){
    	AckBuilder ab = new AckBuilder();
    	byte[] t = ByteBuffer.allocate(8).putLong(timestamp).array();
    	ab.setAck(t);
    	this.sendBuilder(ab, destination);
    	
    }

    public void sendPrivateMessage(String msg, InetAddress destination){
        PrivateChatBuilder pcb = new PrivateChatBuilder();
        pcb.setMessage(msg);
        pcb.setDestination(destination);
        this.sendBuilder(pcb, destination);
    }

    public void sendFile(String path, InetAddress destination){
        FileSplitter fs = new FileSplitter();
        fs.setFile(path);
        FileBuilder[] fba = fs.getBuilders();

        for(FileBuilder fb : fba){
        	sendBuilder(fb, destination);
        }
    }

    public void sendBuilder(DataBuilder b, InetAddress destination){
        PacketBuilder pb = new PacketBuilder();
        pb.setSourceAddress(this.iface_addr);
        pb.setDestinationAddress(destination);
        pb.setDataType(b.getDataType());
        pb.setData(b);

        if(Datatype.getDataTypeAsInt(b.getDataType()) == Datatype.INT_GENERIC_FILE){
            FileBuilder fb = (FileBuilder) b;
            ByteBuffer ts = ByteBuffer.allocate(8).putLong(Timestamp.getCurrentTimeAsLong()+fb.getSeqNr());
            pb.setTimestamp(ts.array());
        }

        byte[] packet = pb.getPacket();

        // Add packet to logging.
        PacketParser pp = new PacketParser(packet);
        // Check if this is an ACK or broadcast message. There is no need to log or ACK an ACK or broadcast message.
        if(Datatype.getDataTypeAsInt(pp.getDataType()) != Datatype.INT_GENERIC_ACK && Datatype.getDataTypeAsInt(pp.getDataType()) != Datatype.INT_BROADCAST_MESSAGE){
            if(this.ackLog.getElement(pp.getTimestamp()) == null){
                // This packet is new to us.
                AckLogElement ackLogElement = new AckLogElement();
                ackLogElement.setIndex(pp.getTimestamp());
                ackLogElement.setData(pp);
                if(Datatype.getDataTypeAsInt(pp.getDataType()) != Datatype.INT_PRIVATE_MESSAGE){
                    for(User u : this.getUsers()){
                        if(u.getIP().equals(pp.getDestinationAddress())) {
                            ackLogElement.setAck(u.getIP(), false);
                        }
                    }
                }else{
                    for(User u : this.getUsers()){
                        if(!u.getIP().equals(this.getMulticastAddress())) {
                            ackLogElement.setAck(u.getIP(), false);
                        }
                    }
                }
                this.ackLog.addElement(ackLogElement);
            }
            // Else, this is a retransmission.
        }

        // Send it.
        this.sender.sendPacket(packet);
    }

    public void forwardPacket(PacketParser pp){
        PacketBuilder pb = new PacketBuilder();
        pb.setSourceAddress(pp.getSourceAddress());
        pb.setDestinationAddress(pp.getDestinationAddress());
        pb.setDataType(pp.getDataType());
        pb.setData(pp.getData());
        pb.setDataLength(pp.getDataLengthAsByteArray());
        pb.setTimestamp(ByteBuffer.allocate(8).putLong(pp.getTimestamp()).array());
        int newHopCount = pp.getHopcount()-1;
        pb.setHopcount(new byte[]{(byte) newHopCount});
        byte[] packet = pb.getPacket();
        this.sender.sendPacket(packet);
    }

    public void sendBroadcastMessage() {
        BroadcastMessageBuilder b = new BroadcastMessageBuilder();
        b.setNick(this.getOwnUser().getName());
        b.setHostname(this.getOwnUser().getHostname());
        this.sendBuilder(b, this.group);
    }

    public void stop(){

    }

    public UDPListener getListener(){
        return this.listener;
    }

    public UDPSender getSender(){
        return this.sender;
    }

    public void addUser(User u){
        boolean exists = false;
        User existingUser = null;
        LinkedList<User> userCopy = new LinkedList<>();
        for(User usr : users){
            userCopy.add(usr);
        }
        for(User user : userCopy){
            if(user.getIP().getHostName().equals(u.getIP().getHostName())){
                exists = true;
                existingUser = user;
            }
        }
        if(exists){
            this.users.remove(existingUser);
            existingUser.setLastSeen(Timestamp.getCurrentTimeAsLong());
            this.users.add(existingUser);
        }else{
            this.users.add(u);
        }
        updateGUI();
    }

    public void removeUser(User u){
        boolean exists = false;
        User existingUser = null;
        LinkedList<User> userCopy = new LinkedList<>();
        for(User usr : users){
            userCopy.add(usr);
        }
        for(User user : userCopy){
            if(user.getIP().getHostName().equals(u.getIP().getHostName())){
                exists = true;
                existingUser = user;
            }
        }
        if(!u.getIP().equals(this.group)){
            LOGGER.log(Level.INFO, "Removing user "+u.getName()+" from connected users.");
            if(exists){
                this.users.remove(existingUser);
                updateGUI();
            }
        }else{
            LOGGER.log(Level.INFO, "Someone tried to remove Educafe from users. Attempt blocked.");
        }
    }

    public User getUser(String name){
        for(User u : this.users){
            if(u.getName().equals(name)){
                return u;
            }
        }
        return null;
    }

    public User getUser(InetAddress ip){
        for(User u : this.users){
            if(u.getIP().equals(ip)){
                return u;
            }
        }
        return null;
    }

    public int getPort(){
        return this.port;
    }

    public LinkedList<User> getUsers(){
        return this.users;
    }

    public User getOwnUser(){
        return this.ownUser;
    }

    public InetAddress getMulticastAddress() {
        return this.group;
    }
    
    public void updateGUI() {
		setChanged();
		notifyObservers();
	}

    public AckLog getAckLog(){
        return this.ackLog;
    }
    
    public CaUI getChatGUI(){
        return this.chatGui;
    }
    
    /*

    SAMPCA Hooks:
    - Send message
    - Stop
    - Upload file (datatype, data)

     */

}
