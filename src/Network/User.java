package Network;

import java.net.DatagramPacket;
import java.net.InetAddress;

/**
 * Created by kevin on 4/7/14.
 */
public interface User {

    public void setName();
    public void setIP();
    public void setPort();
    public void logChatMessage(String msg);
    public void logSpecialContent(String mimetype, byte[] data);

    public String getName();
    public InetAddress getIP();
    public int getPort();
    public String getChatLog(int amount);

}
