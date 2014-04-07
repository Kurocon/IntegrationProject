import Network.UDPClient;
import Network.UDPServer;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by kevin on 4/7/14.
 */
public class SAMPCA {

    private static final Logger LOGGER = Logger.getLogger(SAMPCA.class.getName());

    private UDPServer server;
    private UDPClient client;

    private int port;
    private String username;

    private Thread serverThread;
    private Thread clientThread;

    public static void main(String[] args){
        SAMPCA app = new SAMPCA(5555, "Kurocon");
    }

    public SAMPCA(int port, String username){
        LOGGER.log(Level.INFO, "SAMPCA is starting...");

        this.port = port;
        this.username = username;
        this.startServer();
        this.startClient();

        LOGGER.log(Level.INFO, "SAMPCA successfully started");
    }

    private void startServer(){
        this.server = new UDPServer(this.port);
        this.serverThread = new Thread(this.server);
        this.serverThread.start();
    }

    private void startClient(){
        this.client = new UDPClient("localhost", this.port);
        this.clientThread = new Thread(this.client);
        this.clientThread.start();
    }

}
