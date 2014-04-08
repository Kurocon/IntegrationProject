package Network;

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
    private static final int PACKET_SIZE = 1024;

    private MulticastSocket socket = null;
    private BufferedReader inFromUser = null;
    private InetAddress group = null;
    private int port = -1;

    private DatagramPacket sendPacket = null;
    private byte[] sendData = new byte[PACKET_SIZE];

    public boolean should_run = true;

    public UDPSender(MulticastSocket socket, InetAddress group, int port){
        this.socket = socket;
        this.group = group;
        this.port = port;
    }

    @Override
    public void run() {
        inFromUser = new BufferedReader(new InputStreamReader(System.in));

        LOGGER.log(Level.INFO, "Sender is ready to send!");

        while(should_run){

            String sentence = null;
            try {
                sentence = inFromUser.readLine();
            } catch (IOException e) {
                LOGGER.log(Level.WARNING, "Could not read from stdin! [" + e.getMessage() + "]");
            }

            if(sentence != null && sentence.equals("/exit")){
                this.close();
            }

            if(sentence != null){
                sendData = sentence.getBytes();

                sendPacket = new DatagramPacket(sendData, sendData.length, this.group, this.port);

                try {
                    this.socket.send(sendPacket);
                } catch (IOException e) {
                    LOGGER.log(Level.WARNING, "Could not send message to client! [" + e.getMessage() + "]");
                    e.printStackTrace();
                }
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
}
