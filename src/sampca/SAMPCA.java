package sampca;

import java.io.IOException;
import java.net.*;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

import gui.CaUI;
import network.Security;
import network.UDPListener;
import network.UDPSender;

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

    private Security crypto;

    private MulticastSocket socket;
    private InetAddress group;

    private Thread listenerThread;
    private Thread senderThread;

    private CaUI chatGui = null;

    public boolean finished = false;

    public static void main(String[] args){
        new SAMPCA(5555, "228.133.102.88", "Kurocon", "testpassword");
    }

    public SAMPCA(int port, String ip, String username, String password){
        LOGGER.log(Level.INFO, "SAMPCA is starting...");

        this.port = port;
        this.ip = ip;
        this.username = username;
        this.password = password;

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

        this.startListener();
        this.startSender();

        LOGGER.log(Level.INFO, "SAMPCA successfully started");

        this.openChatGui();
    }

    private void startListener(){
        this.listener = new UDPListener(this.socket);
        this.listener.setCrypto(this.crypto);
        this.listenerThread = new Thread(this.listener);
        this.listenerThread.start();
    }

    private void startSender(){
        this.sender = new UDPSender(this.socket, this.group, this.port);
        this.sender.setCrypto(this.crypto);
        this.senderThread = new Thread(this.sender);
        this.senderThread.start();
    }

    private void openChatGui(){
        this.chatGui = new CaUI(this);
    }

    /*

    SAMPCA Hooks:
    - Send message
    - Stop
    - Upload file (datatype, data)

     */

}
