package Network;

import java.io.*;
import java.net.*;

/**
 * Created by kevin on 4/7/14.
 */
public class UDPClient {

    public static void main(String[] args) throws Exception{

        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

        DatagramSocket clientSocket = new DatagramSocket();

        InetAddress IPAddress = InetAddress.getByName("192.168.5.2");
        int port = 5555;

        byte[] sendData;

        while(true){

            String sentence = inFromUser.readLine();

            if(sentence.equals("exit")){
                break;
            }

            sendData = sentence.getBytes();

            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);

            clientSocket.send(sendPacket);
        }

        byte[] receiveData = new byte[1024];

        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

        clientSocket.receive(receivePacket);

        String modifiedSentence = new String(receivePacket.getData());

        System.out.println("FROM SERVER: "+modifiedSentence);

        clientSocket.close();

    }

}
