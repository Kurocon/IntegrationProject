package network;

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
    
    public void setHostname(String name){
    	this.hostname = name;
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
        this.log.addElement(msg);
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
}
