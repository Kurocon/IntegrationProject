package network;

import protocol.Timestamp;

import java.net.InetAddress;

/**
 * Created by kevin on 4/10/14.
 */
public class UDPUser implements User {

    private String name;
    private String hostname;
    private InetAddress ip;
    private int port;
    private Log log;
    private FileLog files;
    private long lastSeen;

    public UDPUser(){
        //this.log = new ChatLog();
        //this.files = new FileLog();
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    @Override
    public void setIP(InetAddress ip) {
        this.ip=ip;
    }

    @Override
    public void setPort(int port) {
        this.port=port;
    }

    @Override
    public void logChatMessage(String msg) {
        ChatLogElement cle = new ChatLogElement();
        cle.setIndex(Timestamp.getCurrentTimeAsLong());
        cle.setData(msg);
        cle.setUser(this);
        this.log.addElement(cle);
    }

    @Override
    public void logSpecialContent(String filename, byte[] datatype, byte[] data) {
        this.files.addElement(filename, datatype, data);
    }

    @Override
    public void setLastSeen(long currentTimeAsLong) {
        this.lastSeen = currentTimeAsLong;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getHostname() {
        return this.hostname;
    }

    @Override
    public InetAddress getIP() {
        return this.ip;
    }

    @Override
    public int getPort() {
        return this.port;
    }

    @Override
    public long getLastSeen() {
        return this.lastSeen;
    }

    public String toString(){
    	return this.name;
    }
}
