package network;

import protocol.Datatype;

import java.net.DatagramPacket;
import java.net.InetAddress;

/**
 * Created by kevin on 4/7/14.
 */
public interface User {

    public void setName(String name);
    public void setIP(InetAddress ip);
    public void setPort(int port);
    public void logChatMessage(String msg);
    public void logSpecialContent(String filename, byte[] datatype, byte[] data);
    public void setLastSeen(long currentTimeAsLong);

    public String getName();
    public InetAddress getIP();
    public int getPort();
    public long getLastSeen();
}
