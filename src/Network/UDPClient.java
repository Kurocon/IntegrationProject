package Network;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by kevin on 4/7/14.
 */
public class UDPClient implements Client, Runnable {

    private static final Logger LOGGER = Logger.getLogger(UDPServer.class.getName());
    private static final int PACKET_SIZE = 1024;

    private DatagramSocket clientSocket = null;
    private BufferedReader inFromUser = null;

    private DatagramPacket sendPacket = null;
    private byte[] sendData = new byte[PACKET_SIZE];

    private InetAddress IPAddress = null;
    private int port = -1;

    public boolean should_run = true;

    public UDPClient(String ip, int port){
        try{
            this.IPAddress = InetAddress.getByName(ip);
        } catch (UnknownHostException e) {
            LOGGER.log(Level.SEVERE, "Could not find the IP address! [" + e.getMessage() + "]");
            System.exit(1);
        }
        this.port = port;
    }

    @Override
    public void run() {
        LOGGER.log(Level.INFO, "UDPClient is starting...");

        inFromUser = new BufferedReader(new InputStreamReader(System.in));

        try {
            clientSocket = new DatagramSocket();
        } catch (SocketException e) {
            LOGGER.log(Level.SEVERE, "Could not open a client socket! ["+e.getMessage()+"]");
            System.exit(1);
        }

        LOGGER.log(Level.INFO, "Ready to receive messages!");

        while(should_run){

            String sentence = null;
            try {
                sentence = inFromUser.readLine();
            } catch (IOException e) {
                LOGGER.log(Level.WARNING, "Could not read from stdin! [" + e.getMessage() + "]");
            }

            if(sentence.equals("/exit")){
                this.close();
            }

            sendData = sentence.getBytes();

            sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);

            try {
                clientSocket.send(sendPacket);
            } catch (IOException e) {
                LOGGER.log(Level.WARNING, "Could not send message to client! [" + e.getMessage() + "]");
            }
        }
    }

    @Override
    public void close(){
        LOGGER.log(Level.INFO, "Stopping UDPClient...");

        this.should_run = false;
        this.clientSocket.close();
        try {
            this.inFromUser.close();
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Could not cleanly close stdin! ["+e.getMessage()+"]");
        }
        System.exit(0);
    }
}
