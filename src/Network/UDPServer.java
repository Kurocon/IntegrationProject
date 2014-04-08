package Network;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Listener that listens for messages in the UDP Multicast Group.
 * Created by kevin on 4/7/14.
 */
public class UDPServer implements Server, Runnable {

    private static final Logger LOGGER = Logger.getLogger(UDPServer.class.getName());
    private static final int PACKET_SIZE = 1024;

    private boolean should_run = true;

    private UDPHandler handler = null;

    private MulticastSocket socket = null;
    private DatagramPacket receivePacket;

    private byte[] receiveData = new byte[PACKET_SIZE];

    public UDPServer(MulticastSocket socket){
        this.socket = socket;
        this.handler = new UDPHandler();
    }

    @Override
    public void run() {
        LOGGER.log(Level.INFO, "Listener is ready for connections!");

        while(should_run){

            this.receiveData = new byte[PACKET_SIZE];

            this.receivePacket = new DatagramPacket(receiveData, receiveData.length);

            try {
                this.socket.receive(receivePacket);
            } catch (IOException e) {
                LOGGER.log(Level.WARNING, "Could not receive packet from network! ["+e.getMessage()+"]");
            }

            String sentence = new String(receivePacket.getData());

            sentence = sentence.substring(0,sentence.indexOf(" "));

            InetAddress IPAddress = receivePacket.getAddress();

            int port = receivePacket.getPort();

            LOGGER.log(Level.INFO, "Received data from "+IPAddress.getHostName()+":"+port+": "+sentence);

            this.handler.handleInput(receivePacket);

        }
    }

    public void close(){
        LOGGER.log(Level.INFO, "Stopping listener...");

        this.should_run = false;
        this.socket = null;
    }
}
