package oldNetwork;

/**
 * Created by kevin on 4/7/14.
 */
public interface PacketBuilder {
    public boolean setSourcePort(int port);
    public int getSourcePort();
    public boolean setDestinationPort(int port);
    public int getDestinationPort();
    public boolean setLength();
    public int getLength();
    public boolean setChecksum(byte[] source_ip, byte[] destination_ip);
    public int getChecksum();
    public boolean setPacket(byte[] source_ip, byte[] destination_ip);
    public byte[] getPacket();
}
