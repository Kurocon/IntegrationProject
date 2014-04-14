package network;

import protocol.Timestamp;
import protocol.parsers.PacketParser;

import java.net.InetAddress;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by kevin on 4/11/14.
 */
public class AckLogElement implements LogElement<Long, PacketParser> {

    private Long index;
    private PacketParser data;
    private HashMap<InetAddress, Boolean> acks = new HashMap<>();

    @Override
    public void setIndex(Long timestamp) {
        this.index = timestamp;
    }

    @Override
    public void setData(PacketParser bytes) {
        this.data = bytes;
    }

    public Long getIndex(){
        return this.index;
    }

    public PacketParser getData(){
        return this.data;
    }

    public void setAck(InetAddress i, boolean b){
        this.acks.put(i,b);
    }

    public boolean getAck(InetAddress i){
        return this.acks.get(i);
    }

    public Collection<Boolean> getAcks(){
        return this.acks.values();
    }

    public java.util.Set<InetAddress> getAckIps(){
        return this.acks.keySet();
    }
}
