package network;

import protocol.Datatype;
import protocol.PacketParser;
import protocol.WHASProtocol;

import java.net.DatagramPacket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Packet handler to handle incoming input packets and forwarding them to the right method in the protocol.
 * Created by kevin on 4/7/14.
 */
public class UDPPacketHandler implements PacketHandler {

    private static final Logger LOGGER = Logger.getLogger(UDPListener.class.getName());

    WHASProtocol protocol = null;

    public UDPPacketHandler(){
        LOGGER.log(Level.INFO, "UDPPacketHandler is starting...");
        this.protocol = new WHASProtocol();
        LOGGER.log(Level.INFO, "Ready to handle input from server!");
    }

    @Override
    public void handleInput(DatagramPacket packet) {
        // My god, we received something.
        // Throw it through a PacketParser!
        PacketParser pp = new PacketParser(packet.getData());

        switch (Datatype.getDataTypeAsInt(pp.getDataType())){
            case Datatype.INT_GENERIC_ACK:
                this.protocol.generic_ack(pp);
                break;
            case Datatype.INT_BROADCAST_MESSAGE:
                this.protocol.broadcast_message(pp);
                break;
            case Datatype.INT_CHAT_MESSAGE:
                this.protocol.chat_message(pp);
                break;
            case Datatype.INT_PRIVATE_MESSAGE:
                this.protocol.private_message(pp);
                break;
            case Datatype.INT_RESERVED_TYPE_1:
                this.protocol.reserved_type1(pp);
                break;
            case Datatype.INT_RESERVED_TYPE_2:
                this.protocol.reserved_type2(pp);
                break;
            case Datatype.INT_RESERVED_TYPE_3:
                this.protocol.reserved_type3(pp);
                break;
            case Datatype.INT_RESERVED_TYPE_4:
                this.protocol.reserved_type4(pp);
                break;
            case Datatype.INT_RESERVED_TYPE_5:
                this.protocol.reserved_type5(pp);
                break;
            case Datatype.INT_RESERVED_TYPE_6:
                this.protocol.reserved_type6(pp);
                break;
            case Datatype.INT_RESERVED_TYPE_7:
                this.protocol.reserved_type7(pp);
                break;
            case Datatype.INT_RESERVED_TYPE_8:
                this.protocol.reserved_type8(pp);
                break;
            case Datatype.INT_RESERVED_TYPE_9:
                this.protocol.reserved_type9(pp);
                break;
            case Datatype.INT_RESERVED_TYPE_10:
                this.protocol.reserved_type10(pp);
                break;
            case Datatype.INT_RESERVED_TYPE_11:
                this.protocol.reserved_type11(pp);
                break;
            case Datatype.INT_RESERVED_TYPE_12:
                this.protocol.reserved_type12(pp);
                break;
            case Datatype.INT_GENERIC_FILE:
                this.protocol.generic_file(pp);
                break;
            case Datatype.INT_IMAGE_FILE_JPEG:
                this.protocol.image_file_jpeg(pp);
                break;
            case Datatype.INT_IMAGE_FILE_PNG:
                this.protocol.image_file_png(pp);
                break;
            case Datatype.INT_IMAGE_FILE_BMP:
                this.protocol.image_file_bmp(pp);
                break;
            case Datatype.INT_IMAGE_FILE_GIF:
                this.protocol.image_file_gif(pp);
                break;
        }

    }
}
