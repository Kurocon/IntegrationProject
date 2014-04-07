package Network;

import javax.net.ssl.SSLEngineResult;
import javax.xml.crypto.Data;
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by kevin on 4/7/14.
 */
public class UDPServer implements Server, Runnable {

    private static final Logger LOGGER = Logger.getLogger(UDPServer.class.getName());
    private static final int PACKET_SIZE = 1024;

    private int port;
    private boolean should_run = true;

    private UDPHandler handler = null;

    private DatagramSocket serverSocket = null;
    private DatagramPacket receivePacket;

    private byte[] receiveData = new byte[PACKET_SIZE];

    public UDPServer(int port){
        this.port = port;
        this.handler = new UDPHandler();
    }

    @Override
    public void run() {
        LOGGER.log(Level.INFO, "UDPServer is starting...");
        try {
            serverSocket = new DatagramSocket(this.port);
        } catch (SocketException e) {
            LOGGER.log(Level.SEVERE, "Could not create server socket! [" + e.getMessage() + "]");
            System.exit(1);
        }

        LOGGER.log(Level.INFO, "Ready to receive clients.");

        while(should_run){

            this.receiveData = new byte[PACKET_SIZE];

            for(int i=0; i>this.receiveData.length; i++){
                this.receiveData[i] = 0;
            }

            this.receivePacket = new DatagramPacket(receiveData, receiveData.length);

            try {
                serverSocket.receive(receivePacket);
            } catch (IOException e) {
                LOGGER.log(Level.WARNING, "Could not receive packet from server! ["+e.getMessage()+"]");
            }

            String sentence = new String(receivePacket.getData());

            sentence = sentence.substring(0,sentence.indexOf(" "));

            LOGGER.log(Level.INFO, "RECEIVED: "+sentence);

            InetAddress IPAddress = receivePacket.getAddress();

            int port = receivePacket.getPort();

            this.handler.handleInput(receivePacket);

        }
    }

    public void close(){
        LOGGER.log(Level.INFO, "Stopping UDPServer...");

        this.should_run = false;
        this.serverSocket.close();
        System.exit(0);
    }
}
