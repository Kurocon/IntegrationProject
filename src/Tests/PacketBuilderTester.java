package Tests;

import OldNetwork.UDPPacketBuilder;

/**
 * Created by kevin on 4/7/14.
 */
public class PacketBuilderTester {

    public static void main(String[] args){
        PacketBuilderTester test = new PacketBuilderTester();
    }

    public PacketBuilderTester(){
        UDPPacketBuilder b = new UDPPacketBuilder();
        b.setSourcePort(1000);
        b.setDestinationPort(1001);
        byte[] data = "Hallo, dit is data.".getBytes();
        b.setData(data);

        b.setPacket(new byte[]{(byte) 192, (byte) 168, (byte) 5, (byte) 4}, new byte[]{(byte) 192, (byte) 168, (byte) 5, (byte) 3}); // SRC: 192.168.5.4, DEST: 192.168.5.3

        byte[] packet = b.getPacket();

        System.out.println(b.byteArrayToHex(packet));
    }

}
