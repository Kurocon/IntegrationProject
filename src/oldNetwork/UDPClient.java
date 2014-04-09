package oldNetwork;

import java.io.IOException;

/**
 * Created by kevin on 4/7/14.
 */

public class UDPClient implements Client {

    public static String connect_ip;
    public static int connect_port;

    private UDPCommunicator communicator;
    private Handler handler;

    public UDPClient(Handler handler, String ip, int port){
        this.handler = handler;
        this.connect_ip = ip;
        this.connect_port = port;
        try {
            this.communicator = new UDPCommunicator(this.handler, this.connect_ip, this.connect_port);
            new Thread(communicator).start();
            Thread.sleep(100);
        } catch (InterruptedException e) { }
    }

    public void send(byte[] data) {
        if (communicator.out != null) {
            try {
                communicator.out.writeInt(data.length);
                communicator.out.write(data);
                communicator.out.flush();
            } catch (IOException e) {
                System.err.println("Couldn't write socket: " + e.getMessage());
            }
        } else {
            System.err.println("Didn't write socket: not connected");
        }
    }



}
