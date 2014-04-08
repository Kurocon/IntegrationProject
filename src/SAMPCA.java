import Network.UDPClient;
import Network.UDPServer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main class to start SAMPCA.
 *
 * Created by kevin on 4/7/14.
 */
public class SAMPCA {

    private static final Logger LOGGER = Logger.getLogger(SAMPCA.class.getName());

    private UDPServer server;
    private UDPClient client;

    private String ip;
    private int port;
    private String username;

    private MulticastSocket socket;
    private InetAddress group;

    private Thread serverThread;
    private Thread clientThread;

    public static void main(String[] args){
        new SAMPCA(5555, "228.133.102.88", "Kurocon");
    }

    public SAMPCA(int port, String ip, String username){
        LOGGER.log(Level.INFO, "SAMPCA is starting...");

        this.port = port;
        this.ip = ip;
        this.username = username;

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
            this.socket.joinGroup(group);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.startListener();
        this.startSender();

        LOGGER.log(Level.INFO, "SAMPCA successfully started");
    }

    private void startListener(){
        this.server = new UDPServer(this.socket);
        this.serverThread = new Thread(this.server);
        this.serverThread.start();
    }

    private void startSender(){
        this.client = new UDPClient(this.socket, this.group, this.port);
        this.clientThread = new Thread(this.client);
        this.clientThread.start();
    }

}
