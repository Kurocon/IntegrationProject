package OldNetwork;

/**
 * Created by kevin on 4/7/14.
 */
public interface Handler {
    public void sendData(byte[] data);
    public void dataReceived(byte[] data);
}
