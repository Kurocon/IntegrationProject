package Protocol;

import Exceptions.ArrayTooLongException;
import Exceptions.WrongArrayLengthException;

import java.lang.reflect.Array;
import java.net.InetAddress;
import java.util.Arrays;

/**
 * Created by kevin on 4/8/14.
 */
public class PacketBuilder {

    private byte[] source_address;
    private byte[] destination_address;
    private byte[] hopcount = new byte[]{(byte) 255};
    private byte[] version = new byte[]{(byte) 1};
    private byte[] dataType;
    private byte[] timestamp;
    private byte[] data;

    /*
        HEADERS:
          0       7 8      15 16     23 24     31
         +---------+---------+---------+---------+
         |    src_address    |    dest_address   |
         +---------+---------+---------+---------+
         |hop |ver | datatype|     timestamp     |
         +----+----+---------+---------+---------+

     */

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
        if(timestamp.length != 4){
            throw new WrongArrayLengthException("Timestamp must be 4 bytes long.");
        }else{
            this.timestamp = timestamp;
        }
    }

    public void setData(byte[] data){
        if(data.length < 1008){
            byte[] newdata = new byte[1008];
            System.arraycopy(data, 0, newdata, 0, data.length);
            this.data = newdata;
        }else if(data.length > 1008){
            throw new ArrayTooLongException("Data can be a maximum of 1008 bytes long.");
        }else{
            this.data = data;
        }
    }

    public byte[] getPacket(){
        byte[] header = this.getHeader();
        byte[] packet = this.concatByteArrays(header, this.data);
        return packet;
    }

    private byte[] getHeader(){
        byte[] octet1       = this.concatByteArrays(this.source_address, this.destination_address);
        byte[] hopVersion   = this.concatByteArrays(this.hopcount, this.version);
        byte[] hopverdtype  = this.concatByteArrays(hopVersion, this.dataType);
        byte[] octet2       = this.concatByteArrays(hopverdtype, this.timestamp);

        return this.concatByteArrays(octet1, octet2);
    }

    private byte[] concatByteArrays(byte[] a, byte[] b){
        int aLen = a.length;
        int bLen = b.length;
        byte[] c = new byte[aLen+bLen];
        System.arraycopy(a, 0, c, 0, aLen);
        System.arraycopy(b, 0, c, aLen, bLen);
        return c;
    }
}
