package protocol.builders;

import exceptions.ArrayTooLongException;
import exceptions.WrongArrayLengthException;
import protocol.Timestamp;
import sampca.SAMPCA;

import java.net.InetAddress;
import java.nio.ByteBuffer;

/**
 * PacketBuilder to build packets.
 * Created by kevin on 4/8/14.
 */
public class PacketBuilder {

    private byte[] source_address;
    private byte[] destination_address;
    private byte[] hopcount;
    private byte[] version;
    private byte[] dataType;
    private byte[] timestamp;
    private byte[] dataLength;
    private byte[] reserved;
    private byte[] data;
    private DataBuilder dataBuilder;

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

    public PacketBuilder(){
        this.hopcount = new byte[]{0x0F};
        this.version = new byte[]{2};
        this.timestamp = ByteBuffer.allocate(8).putLong(Timestamp.getCurrentTimeAsLong()).array();
        this.reserved = new byte[]{0x00,0x00};
    }

    public void setSourceAddress(InetAddress i){
        this.source_address = i.getAddress();
    }

    public void setDestinationAddress(InetAddress i){
        this.destination_address = i.getAddress();
    }

    public void setHopcount(byte[] hopcount){
        if(hopcount.length != 1){
            throw new WrongArrayLengthException("Hopcount must be 1 bytes long.");
        }else{
            this.hopcount = hopcount;
        }
    }

    public void setDataType(byte[] dataType){
        if(dataType.length != 2){
            throw new WrongArrayLengthException("DataType must be 2 bytes long.");
        }else{
            this.dataType = dataType;
        }
    }

    public void setTimestamp(byte[] timestamp){
        if(timestamp.length != 8){
            throw new WrongArrayLengthException("Timestamp must be 8 bytes long.");
        }else{
            this.timestamp = timestamp;
        }
    }

    public void setReserved(byte[] reserved){
        if(reserved.length != 2){
            throw new WrongArrayLengthException("Reserved must be 4 bytes long.");
        }else{
            this.reserved = reserved;
        }
    }

    public void setData(DataBuilder b){
        this.dataBuilder = b;
        byte[] data = this.dataBuilder.getData();
        byte[] length = this.dataBuilder.getDataLength();

        if(data.length < (SAMPCA.MAX_PACKET_SIZE - SAMPCA.GENERAL_HEADER_SIZE)){
            byte[] newdata = new byte[SAMPCA.MAX_PACKET_SIZE - SAMPCA.GENERAL_HEADER_SIZE];
            System.arraycopy(data, 0, newdata, 0, data.length);
            this.data = newdata;
        }else if(data.length > (SAMPCA.MAX_PACKET_SIZE - SAMPCA.GENERAL_HEADER_SIZE)){
            throw new ArrayTooLongException("Data can be a maximum of "+(SAMPCA.MAX_PACKET_SIZE - SAMPCA.GENERAL_HEADER_SIZE)+" bytes long.");
        }else{
            this.data = data;
        }

        this.dataLength = length;
    }

    public void setData(byte[] data){
        byte[] length = ByteBuffer.allocate(2).putShort((short) data.length).array();

        if(data.length < (SAMPCA.MAX_PACKET_SIZE - SAMPCA.GENERAL_HEADER_SIZE)){
            byte[] newdata = new byte[SAMPCA.MAX_PACKET_SIZE - SAMPCA.GENERAL_HEADER_SIZE];
            System.arraycopy(data, 0, newdata, 0, data.length);
            this.data = newdata;
        }else if(data.length > (SAMPCA.MAX_PACKET_SIZE - SAMPCA.GENERAL_HEADER_SIZE)){
            throw new ArrayTooLongException("Data can be a maximum of "+(SAMPCA.MAX_PACKET_SIZE - SAMPCA.GENERAL_HEADER_SIZE)+" bytes long.");
        }else{
            this.data = data;
        }

        this.dataLength = length;
    }

    public byte[] getPacket(){
        byte[] header = this.getHeader();
        byte[] packet = this.concatByteArrays(header, this.data);
        return packet;
    }

    private byte[] getHeader(){
        byte[] octet1       = this.concatByteArrays(this.source_address, this.destination_address);
        byte[] octet2       = this.concatThreeByteArrays(this.hopcount, this.version, this.dataType);
        byte[] octet3       = this.concatThreeByteArrays(this.timestamp, this.dataLength, this.reserved);
        return this.concatThreeByteArrays(octet1, octet2, octet3);
    }

    private byte[] concatByteArrays(byte[] a, byte[] b){
        int aLen = a.length;
        int bLen = b.length;
        byte[] c = new byte[aLen+bLen];
        System.arraycopy(a, 0, c, 0, aLen);
        System.arraycopy(b, 0, c, aLen, bLen);
        return c;
    }

    private byte[] concatThreeByteArrays(byte[] a, byte[] b, byte[] c){
        return this.concatByteArrays(this.concatByteArrays(a,b),c);
    }
}
