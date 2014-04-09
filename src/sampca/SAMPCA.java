package sampca;

import java.io.IOException;
import java.net.*;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    private UDPListener server;
    private UDPSender client;

    private String ip;
    private int port;
    private String username;
    private NetworkInterface iface;

    private MulticastSocket socket;
    private InetAddress group;

    private Thread serverThread;
    private Thread clientThread;

    public boolean finished = false;

    public static void main(String[] args){
        new SAMPCA(5555, "228.133.102.88", "Kurocon");
    }

    public SAMPCA(int port, String ip, String username){
        LOGGER.log(Level.INFO, "SAMPCA is starting...");

        this.port = port;
        this.ip = ip;
        this.username = username;

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
    }

    private void startListener(){
        this.server = new UDPListener(this.socket);
        this.serverThread = new Thread(this.server);
        this.serverThread.start();
    }

    private void startSender(){
        this.client = new UDPSender(this.socket, this.group, this.port);
        this.clientThread = new Thread(this.client);
        this.clientThread.start();
    }

}
