package oldNetwork;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by kevin on 4/7/14.
 */
public class UDPCommunicator implements Runnable {

    DataOutputStream out;
    DataInputStream in;
    Handler handler;

    String connect_ip;
    int connect_port;

    public UDPCommunicator(Handler handler, String ip, int port){
        this.handler = handler;
        this.connect_ip = ip;
        this.connect_port = port;
    }

    public void run() {
        Socket clientSocket = null;
        try {
            clientSocket = new Socket(connect_ip, connect_port);
            out = new DataOutputStream(clientSocket.getOutputStream());
            in = new DataInputStream(clientSocket.getInputStream());

            while (true) {
                int size = in.readInt();
                byte[] data = new byte[size];
                in.read(data, 0, size);
                handler.dataReceived(data);
            }
        } catch (IOException e) {
            System.err.println("Couldn't read socket: " + e.getMessage());
        } finally {
            try {
                if (clientSocket != null)
                    clientSocket.close();
            } catch (IOException e) { }
        }

        System.err.println("OldNetwork communicator stopped!");
    }
}
