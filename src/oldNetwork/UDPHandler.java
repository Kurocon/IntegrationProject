package oldNetwork;

/**
 * Created by kevin on 4/7/14.
 */
public class UDPHandler implements Handler {

    Client client;

    public UDPHandler(String ip, int port){
        this.client = new UDPClient(this, ip, port);
    }

    @Override
    public void sendData(byte[] data) {
        client.send(data);
    }

    @Override
    public void dataReceived(byte[] data) {

    }
}
