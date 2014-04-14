package network;

import protocol.parsers.PacketParser;

import java.net.InetAddress;
import java.util.HashMap;

/**
 * Created by kevin on 4/11/14.
 */
public class PacketLogElement implements LogElement<Long, PacketParser> {
    private Long index;
    private PacketParser data;

    @Override
    public void setIndex(Long timestamp) {
        this.index = timestamp;
    }

    @Override
    public void setData(PacketParser bytes) {
        this.data = bytes;
    }

    @Override
    public Long getIndex(){
        return this.index;
    }

    @Override
    public PacketParser getData(){
        return this.data;
    }
}
