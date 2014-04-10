package network;

import sampca.SAMPCA;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Listener that listens for messages in the UDP Multicast Group.
 * Created by kevin on 4/7/14.
 */
public class UDPListener implements Listener, Runnable {

    private static final Logger LOGGER = Logger.getLogger(UDPListener.class.getName());
    private static final int PACKET_SIZE = 1024;

    private boolean should_run = true;

    private UDPPacketHandler handler = null;

    private MulticastSocket socket = null;
    private DatagramPacket receivePacket;

    private SAMPCA sampca = null;

    private byte[] receiveData = new byte[PACKET_SIZE];
    private Security crypto;

    public UDPListener(SAMPCA s, MulticastSocket socket){
        this.sampca = s;
        this.socket = socket;
        this.handler = new UDPPacketHandler(this);
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

            InetAddress IPAddress = receivePacket.getAddress();

            int port = receivePacket.getPort();

            // Decryption!
            byte[] data = receivePacket.getData();
            //this.crypto.decryptData(data);
            //this.receivePacket = new DatagramPacket(data, data.length);

            LOGGER.log(Level.INFO, "Received data from "+IPAddress.getHostName()+":"+port);

            this.handler.handleInput(receivePacket);

        }
    }

    public void close(){
        LOGGER.log(Level.INFO, "Stopping listener...");

        this.should_run = false;
        this.socket = null;
    }

    public void setCrypto(Security crypto) {
        this.crypto = crypto;
    }
}
