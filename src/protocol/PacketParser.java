package protocol;

import exceptions.WrongArrayLengthException;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

/**
 * PacketParser to parse packets
 * Created by kevin on 4/9/14.
 */
public class PacketParser {

    private InetAddress dst_addr;
    private InetAddress src_addr;
    private int hopcount;
    private int version;
    private byte[] dataType;
    private long timestamp;
    private int dataLength;
    private int reserved;
    private byte[] data;
    private byte[] packet;

    public PacketParser(byte[] packet){
        this.packet = packet;

        if(packet.length != 1024){
            throw new WrongArrayLengthException("Packet length is not 1024!");
        }else{
            // Parse the packet according to our own headers.
            /*
                HEADERS:
                  0       7 8      15 16     23 24     31
                 +---------+---------+---------+---------+
                 |              src_address              |
                 +---------+---------+---------+---------+
                 |              dest_address             |
                 +---------+---------+---------+---------+
                 |   hop   |   ver   |     datatype      |
                 +---------+---------+---------+---------+
                 |               timestamp               |
                 +---------+---------+---------+---------+
                 |               timestamp               |
                 +---------+---------+---------+---------+
                 |    data_length    |      reserved     |
                 +---------+---------+---------+---------+
             */

            byte[] src_addr = new byte[4];
            byte[] dst_addr = new byte[4];
            byte hopcount;
            byte version;
            byte[] dataType = new byte[2];
            byte[] timestamp = new byte[8];
            byte[] dataLength = new byte[2];
            byte[] reserved = new byte[2];
            byte[] data = new byte[1000];

            System.arraycopy(this.packet, 0, src_addr, 0, 4);
            System.arraycopy(this.packet, 4, dst_addr, 0, 4);
            hopcount = this.packet[8];
            version = this.packet[9];
            System.arraycopy(this.packet, 10, dataType, 0, 2);
            System.arraycopy(this.packet, 12, timestamp, 0, 8);
            System.arraycopy(this.packet, 20, dataLength, 0, 2);
            System.arraycopy(this.packet, 22, reserved, 0, 2);
            System.arraycopy(this.packet, 24, data, 0, 1000);

            try {
                this.src_addr = InetAddress.getByAddress(src_addr);
                this.dst_addr = InetAddress.getByAddress(dst_addr);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }

            this.hopcount = (int) hopcount;
            this.version = (int) version;
            this.dataType = dataType;
            this.timestamp = ByteBuffer.allocate(8).put(timestamp).getLong();
            this.dataLength = ByteBuffer.allocate(2).put(dataLength).getInt();
            this.reserved = ByteBuffer.allocate(2).put(reserved).getInt();
            this.data = data;
        }

    }

    public InetAddress getDestinationAddress() {
        return dst_addr;
    }

    public InetAddress getSourceAddress() {
        return src_addr;
    }

    public int getHopcount() {
        return hopcount;
    }

    public int getVersion() {
        return version;
    }

    public byte[] getDataType() {
        return dataType;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public int getDataLength() {
        return dataLength;
    }

    public int getReserved() {
        return reserved;
    }

    public byte[] getData(){
        return data;
    }
}