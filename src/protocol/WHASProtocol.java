package protocol;

import network.UDPPacketHandler;

/**
 * Protocol implementation
 * Created by kevin on 4/7/14.
 */
public class WHASProtocol implements Protocol {

    private UDPPacketHandler handler = null;

    public WHASProtocol(UDPPacketHandler h){
        this.handler = h;
    }

    /*
            SYSTEM AND CHAT HANDLERS
     */

    @Override
    public void generic_ack(PacketParser data) {

    }

    @Override
    public void broadcast_message(PacketParser data) {

    }

    @Override
    public void chat_message(PacketParser data) {

    }

    @Override
    public void private_message(PacketParser data) {

    }

    /*
            RESERVED TYPE HANDLERS
     */

    @Override
    public void reserved_type1(PacketParser data) {

    }

    @Override
    public void reserved_type2(PacketParser data) {

    }

    @Override
    public void reserved_type3(PacketParser data) {

    }

    @Override
    public void reserved_type4(PacketParser data) {

    }

    @Override
    public void reserved_type5(PacketParser data) {

    }

    @Override
    public void reserved_type6(PacketParser data) {

    }

    @Override
    public void reserved_type7(PacketParser data) {

    }

    @Override
    public void reserved_type8(PacketParser data) {

    }

    @Override
    public void reserved_type9(PacketParser data) {

    }

    @Override
    public void reserved_type10(PacketParser data) {

    }

    @Override
    public void reserved_type11(PacketParser data) {

    }

    @Override
    public void reserved_type12(PacketParser data) {

    }

    /*
            FILE HANDLERS
     */

    @Override
    public void generic_file(PacketParser data) {

    }

    @Override
    public void image_file_jpeg(PacketParser data) {

    }

    @Override
    public void image_file_png(PacketParser data) {

    }

    @Override
    public void image_file_bmp(PacketParser data) {

    }

    @Override
    public void image_file_gif(PacketParser data) {

    }
}
