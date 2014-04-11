package network;

import protocol.Datatype;
import protocol.parsers.PacketParser;
import sampca.SAMPCA;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Sender to send messages from stdin to the multicast group.
 * Created by kevin on 4/7/14.
 */
public class UDPSender implements Sender, Runnable {

    private static final Logger LOGGER = Logger.getLogger(UDPListener.class.getName());

    private MulticastSocket socket = null;
    private BufferedReader inFromUser = null;
    private InetAddress group = null;
    private int port = -1;
    private SAMPCA sampca = null;

    public boolean should_run = true;
    private Security crypto;

    public UDPSender(SAMPCA s, MulticastSocket socket, InetAddress group, int port){
        this.sampca = s;
        this.socket = socket;
        this.group = group;
        this.port = port;
    }

    @Override
    public void run() {
        inFromUser = new BufferedReader(new InputStreamReader(System.in));

        LOGGER.log(Level.INFO, "Sender is ready to send!");

        while(should_run){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                LOGGER.log(Level.WARNING, "Thread interrupted. ["+e.getMessage()+"]");
            }
        }
    }

    @Override
    public void close(){
        LOGGER.log(Level.INFO, "Stopping sender...");

        this.should_run = false;
        this.socket = null;

        try {
            this.inFromUser.close();
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Could not cleanly close stdin! ["+e.getMessage()+"]");
        }
    }

    public void setCrypto(Security crypto) {
        this.crypto = crypto;
    }

    public void send(DatagramPacket p){
        try {
            this.socket.send(p);
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Could not send message to client! [" + e.getMessage() + "]");
        }
    }

    public void sendPacket(byte[] packet) {
        // Encrypt data!
        this.crypto.encryptData(packet);
        DatagramPacket sendPacket = new DatagramPacket(packet, packet.length, this.group, this.port);
        this.send(sendPacket);
    }

    public SAMPCA getSAMPCA(){
        return this.sampca;
    }
}
