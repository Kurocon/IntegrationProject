package oldNetwork;

import java.nio.ByteBuffer;

/**
 * Created by kevin on 4/7/14.
 */
public class UDPPacketBuilder implements PacketBuilder {

    private final int UDP_PROTOCOL_NUMBER = 17;

    private int source_port = -1;
    private int destination_port = -1;
    private int length;
    private byte[] checksum;
    private byte[] data;
    private byte[] header;

    private byte[] packet;

    public UDPPacketBuilder(){

    }

    public boolean setSourcePort(int port){
        if(port > 0 && port < 65535){
            this.source_port = port;
            return true;
        }else{
            return false;
        }
    }

    public int getSourcePort(){
        return this.source_port;
    }

    public boolean setDestinationPort(int port){
        if(port > 0 && port < 65535){
            this.destination_port = port;
            return true;
        }else{
            return false;
        }
    }

    public int getDestinationPort(){
        return this.destination_port;
    }

    public boolean setLength(){
        if(this.getData() != null){
            this.length = 8 + this.getData().length;
            return true;
        }else{
            return false;
        }
    }

    public int getLength() {
        return this.length;
    }

    public boolean setChecksum(byte[] source_ip, byte[] destination_ip){
        if(this.getData() != null){
            byte[] zeroes = new byte[]{0};
            byte[] protocol = this.intToByte(UDP_PROTOCOL_NUMBER);
            byte[] zeroprotocol = this.concatByteArrays(zeroes, protocol);
            if(this.getLength() < 8){
                this.setLength();
            }
            byte[] udp_length = this.intToByte(this.getLength());

            byte[] octet12 = this.concatByteArrays(source_ip, destination_ip);
            byte[] octet3 = this.concatByteArrays(zeroprotocol, udp_length);
            byte[] psuedo_header = this.concatByteArrays(octet12, octet3);
            byte[] header = this.concatByteArrays(psuedo_header, this.getHeaderWithoutChecksum());

            byte[] to_checksum = this.concatByteArrays(header, this.getData());

            this.checksum = calculateChecksum(to_checksum);

            return true;
        }else{
            return false;
        }
    }

    public int getChecksum() {
        return (this.checksum[0] * 256 + this.checksum[1]);
    }

    public byte[] getChecksumBytes(){
        return this.checksum;
    }

    public boolean setData(byte[] data){
        if(data.length != 0){
            this.data = data;
            return true;
        }else{
            return false;
        }
    }

    public byte[] getData(){
        return this.data;
    }

    public boolean setPacket(byte[] source_ip, byte[] destination_ip){
        if(this.getData() != null && this.getDestinationPort() != -1 && this.getSourcePort() != -1){

            this.setLength();
            this.setChecksum(source_ip, destination_ip);
            this.setHeader();

            this.packet = this.concatByteArrays(this.getHeader(), this.getData());
            return true;
        }else{
            return false;
        }
    }

    public byte[] getPacket() {
        return this.packet;
    }

    private boolean setHeader(){
        // UDP Header looks like this:
        // Octet0: 16 bits source port, 16 bit destination port
        // Octet1: 16 bit length, 16 bit checksum

        if(this.getSourcePort() != -1 && this.getDestinationPort() != -1 && this.getLength() != -1 && this.getChecksumBytes().length != 0){
            byte[] sourceport   = this.intToByte(this.getSourcePort());
            byte[] destinport   = this.intToByte(this.getDestinationPort());
            byte[] length       = this.intToByte(this.getLength());
            byte[] checksum     = this.getChecksumBytes();

            byte[] octet1 = this.concatByteArrays(sourceport, destinport);
            byte[] octet2 = this.concatByteArrays(length, checksum);

            this.header = this.concatByteArrays(octet1, octet2);
            return true;
        }else{
            return false;
        }
    }

    private byte[] getHeaderWithoutChecksum(){
        // UDP Header looks like this:
        // Octet0: 16 bits source port, 16 bit destination port
        // Octet1: 16 bit length, 16 bit checksum

        if(this.getSourcePort() != -1 && this.getDestinationPort() != -1 && this.getLength() != -1 && this.getChecksumBytes().length != 0){
            byte[] sourceport   = this.intToByte(this.getSourcePort());
            byte[] destinport   = this.intToByte(this.getDestinationPort());
            byte[] length       = this.intToByte(this.getLength());
            byte[] checksum     = new byte[]{0,0};

            byte[] octet1 = this.concatByteArrays(sourceport, destinport);
            byte[] octet2 = this.concatByteArrays(length, checksum);

            return this.concatByteArrays(octet1, octet2);
        }else{
            return null;
        }
    }

    public byte[] getHeader(){
        return this.header;
    }

    public byte[] calculateChecksum(byte[] data){
        int i = 0;
        int length = data.length;
        long sum = 0;
        while (length > 0) {
            sum += (data[i++]&0xff) << 8;
            if ((--length)==0) break;
            sum += (data[i++]&0xff);
            --length;
        }

        long check = (~((sum & 0xFFFF)+(sum >> 16)))&0xFFFF;
        return ByteBuffer.allocate(8).putLong(check).array();
    }

    private byte[] intToByte(int port){
        byte[] data = new byte[2];
        data[0] = (byte) (port & 0xFF);
        data[1] = (byte) ((port >> 8) & 0xFF);
        return data;
    }

    public String byteArrayToHex(byte[] a) {
        StringBuilder sb = new StringBuilder();
        for(byte b: a)
            sb.append(String.format("%02x", b&0xff));
        return sb.toString();
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
