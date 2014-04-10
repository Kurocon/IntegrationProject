package protocol;

import protocol.parsers.PacketParser;

/**
 * Protocol interface
 * Created by kevin on 4/7/14.
 */
public interface Protocol {

    public void generic_ack(PacketParser data);
    public void broadcast_message(PacketParser data);
    public void chat_message(PacketParser data);
    public void private_message(PacketParser data);
    public void reserved_type1(PacketParser data);
    public void reserved_type2(PacketParser data);
    public void reserved_type3(PacketParser data);
    public void reserved_type4(PacketParser data);
    public void reserved_type5(PacketParser data);
    public void reserved_type6(PacketParser data);
    public void reserved_type7(PacketParser data);
    public void reserved_type8(PacketParser data);
    public void reserved_type9(PacketParser data);
    public void reserved_type10(PacketParser data);
    public void reserved_type11(PacketParser data);
    public void reserved_type12(PacketParser data);

    public void generic_file(PacketParser data);
    public void image_file_jpeg(PacketParser data);
    public void image_file_png(PacketParser data);
    public void image_file_bmp(PacketParser data);
    public void image_file_gif(PacketParser data);

}
