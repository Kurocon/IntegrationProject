package sampca;

import java.io.IOException;
import java.net.*;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

import gui.CaUI;
import gui.MainUI;
import network.*;
import protocol.*;

/**
 * Main class to start SAMPCA.
 *
 * Created by kevin on 4/7/14.
 */
public class SAMPCA {

    private static final Logger LOGGER = Logger.getLogger(SAMPCA.class.getName());

	public static final String PROGRAM_NAME = "SAMPCA";

    private UDPListener listener;
    private UDPSender sender;

    private String ip;
    private int port;
    private String username;
    private String password;
    private NetworkInterface iface;
    private InetAddress iface_addr;

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
        LOGGER.log(Level.INFO, "SAMPCA is starting...");

        this.port = port;
        this.ip = ip;
        this.username = username;
        this.password = password;

        this.users = new LinkedList<>();

        User main_channel = new UDPUser();
        main_channel.setName("Educafé");
        main_channel.setIP(this.group);
        main_channel.setPort(this.port);
        main_channel.setLastSeen(Timestamp.getCurrentTimeAsLong());
        this.addUser(main_channel);

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

        Enumeration<InetAddress> iface_addrs = this.iface.getInetAddresses();
        InetAddress iface_addr= iface_addrs.nextElement();
        while((iface_addr.getHostAddress().startsWith("fe80:") || iface_addr.getHostAddress().startsWith("2001:")) && iface_addrs.hasMoreElements()){
            this.iface_addr = iface_addrs.nextElement();
        }

        LOGGER.log(Level.INFO, "-- Current configuration: --");
        LOGGER.log(Level.INFO, "ip="+this.ip);
        LOGGER.log(Level.INFO, "port="+this.port);
        LOGGER.log(Level.INFO, "username="+this.username);
        LOGGER.log(Level.INFO, "password="+this.password);
        LOGGER.log(Level.INFO, "interface="+this.iface.getName());
        LOGGER.log(Level.INFO, "interface_ip="+this.iface_addr.getHostAddress());
        LOGGER.log(Level.INFO, "groupip="+this.group.getHostAddress());
        /*
    private String ip;
    private int port;
    private String username;
    private String password;
    private NetworkInterface iface;
    private InetAddress group;
         */

        this.startListener();
        this.startSender();

        LOGGER.log(Level.INFO, "SAMPCA successfully started");

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

    public void sendMessage(String msg){
        this.sendMessage(msg, this.group);
    }

    public void sendMessage(String msg, InetAddress destination){
        ChatBuilder b = new ChatBuilder();
        b.setMessage(msg);
        PacketBuilder pb = new PacketBuilder();
        pb.setSourceAddress(this.iface_addr);
        pb.setDestinationAddress(destination);
        pb.setDataType(Datatype.CHAT_MESSAGE);
        pb.setData(b);
        byte[] packet = pb.getPacket();
        this.sender.sendPacket(packet);
    }

    public void stop(){

    }

    public void sendFile(String filename, byte[] data){

    }

    public void addUser(User u){
        if(this.users.contains(u)){
            this.users.get(this.users.indexOf(u)).setLastSeen(Timestamp.getCurrentTimeAsLong());
        }else{
            this.users.add(u);
        }
    }

    public void removeUser(User u){
        if(!u.getIP().equals(this.group)){
            if(this.users.contains(u)){
                this.users.remove(u);
            }
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

    /*

    SAMPCA Hooks:
    - Send message
    - Stop
    - Upload file (datatype, data)

     */

}
