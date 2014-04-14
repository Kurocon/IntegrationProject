package network;

import protocol.*;
import protocol.parsers.PacketParser;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Packet handler to handle incoming input packets and forwarding them to the right method in the protocol.
 * Created by kevin on 4/7/14.
 */
public class UDPPacketHandler implements PacketHandler {

    private static final Logger LOGGER = Logger.getLogger(UDPListener.class.getName());

    private WHASProtocol protocol = null;
    private UDPListener listener = null;

    public UDPPacketHandler(UDPListener l){
        LOGGER.log(Level.INFO, "UDPPacketHandler is starting...");
        this.listener = l;
        this.protocol = new WHASProtocol(this);
        LOGGER.log(Level.INFO, "Ready to handle input from server!");
    }

    @Override
    public void handleInput(DatagramPacket packet) {
        // My god, we received something.
        // Throw it through a PacketParser!
        PacketParser pp = new PacketParser(packet.getData());

        /*
if is not destination then
    if is source then
        drop packet
    end if
    if hopcount greater than 0 then
        decrease hopcount by 1;
        forward packet
    else
        drop packet
    end if
else
    if hop count equals 0 then
        accept the packet and process;
    else
        drop packet
    end if
end if
         */

        InetAddress dest_addr = pp.getDestinationAddress();
        InetAddress src_addr = pp.getSourceAddress();
        InetAddress our_addr = this.listener.getSAMPCA().getOwnUser().getIP();
        InetAddress mcast_addr = this.listener.getSAMPCA().getMulticastAddress();

        if(mcast_addr.equals(dest_addr)){
            // This is a multicast packet meant for everyone!
            // You gotta share, you gotta care!
            if(pp.getHopcount() > 0 && !our_addr.equals(src_addr)){
                int newHopCount = pp.getHopcount()-1;
                // Forward packet to all.
                this.getListener().getSAMPCA().forwardPacket(pp);
            }else if(pp.getHopcount() <= 0){
                // Hop count expired. Drop it.
                LOGGER.log(Level.INFO, "Dropping packet from "+pp.getSourceAddress().getHostName()+", reason: Hopcount expired.");
                return;
            }
        }else if(!our_addr.equals(dest_addr)){
            if(pp.getHopcount() > 0 && !our_addr.equals(src_addr)){
                int newHopCount = pp.getHopcount()-1;
                // Forward packet to all.
                this.getListener().getSAMPCA().forwardPacket(pp);
            }else{
                // Hop count expired or this is a packet we sent. Drop it.
                if(pp.getHopcount()<=0){
//                    LOGGER.log(Level.INFO, "Dropping packet from "+pp.getSourceAddress().getHostName()+", reason: Hopcount expired.");
                }else if(our_addr.equals(src_addr)){
//                    LOGGER.log(Level.INFO, "Dropping packet from "+pp.getSourceAddress().getHostName()+", reason: This is a packet we have sent.");
                }else{
//                    LOGGER.log(Level.INFO, "Dropping packet from "+pp.getSourceAddress().getHostName()+", reason: Unknown.");
                }
                return;
            }
        }

        // This packet is (also) meant for us! We need to parse it!
        switch (Datatype.getDataTypeAsInt(pp.getDataType())){
            case Datatype.INT_GENERIC_ACK:
//                LOGGER.log(Level.INFO, "Received generic ACK from "+pp.getSourceAddress().getHostName());
                this.protocol.generic_ack(pp);
                break;
            case Datatype.INT_BROADCAST_MESSAGE:
//                LOGGER.log(Level.INFO, "Received broadcast message from "+pp.getSourceAddress().getHostName());
                this.protocol.broadcast_message(pp);
                break;
            case Datatype.INT_CHAT_MESSAGE:
                LOGGER.log(Level.INFO, "Received chat message from "+pp.getSourceAddress().getHostName());
                this.protocol.chat_message(pp);
                break;
            case Datatype.INT_PRIVATE_MESSAGE:
//                LOGGER.log(Level.INFO, "Received private message from "+pp.getSourceAddress().getHostName());
                this.protocol.private_message(pp);
                break;
            case Datatype.INT_RESERVED_TYPE_1:
//                LOGGER.log(Level.INFO, "Received reserved type 1 from "+pp.getSourceAddress().getHostName());
                this.protocol.reserved_type1(pp);
                break;
            case Datatype.INT_RESERVED_TYPE_2:
//                LOGGER.log(Level.INFO, "Received reserved type 2 from "+pp.getSourceAddress().getHostName());
                this.protocol.reserved_type2(pp);
                break;
            case Datatype.INT_RESERVED_TYPE_3:
//                LOGGER.log(Level.INFO, "Received reserved type 3 from "+pp.getSourceAddress().getHostName());
                this.protocol.reserved_type3(pp);
                break;
            case Datatype.INT_RESERVED_TYPE_4:
//                LOGGER.log(Level.INFO, "Received reserved type 4 from "+pp.getSourceAddress().getHostName());
                this.protocol.reserved_type4(pp);
                break;
            case Datatype.INT_RESERVED_TYPE_5:
//                LOGGER.log(Level.INFO, "Received reserved type 5 from "+pp.getSourceAddress().getHostName());
                this.protocol.reserved_type5(pp);
                break;
            case Datatype.INT_RESERVED_TYPE_6:
//                LOGGER.log(Level.INFO, "Received reserved type 6 from "+pp.getSourceAddress().getHostName());
                this.protocol.reserved_type6(pp);
                break;
            case Datatype.INT_RESERVED_TYPE_7:
//                LOGGER.log(Level.INFO, "Received reserved type 7 from "+pp.getSourceAddress().getHostName());
                this.protocol.reserved_type7(pp);
                break;
            case Datatype.INT_RESERVED_TYPE_8:
//                LOGGER.log(Level.INFO, "Received reserved type 8 from "+pp.getSourceAddress().getHostName());
                this.protocol.reserved_type8(pp);
                break;
            case Datatype.INT_RESERVED_TYPE_9:
//                LOGGER.log(Level.INFO, "Received reserved type 9 from "+pp.getSourceAddress().getHostName());
                this.protocol.reserved_type9(pp);
                break;
            case Datatype.INT_RESERVED_TYPE_10:
//                LOGGER.log(Level.INFO, "Received reserved type 10 from "+pp.getSourceAddress().getHostName());
                this.protocol.reserved_type10(pp);
                break;
            case Datatype.INT_RESERVED_TYPE_11:
//                LOGGER.log(Level.INFO, "Received reserved type 11 from "+pp.getSourceAddress().getHostName());
                this.protocol.reserved_type11(pp);
                break;
            case Datatype.INT_RESERVED_TYPE_12:
//                LOGGER.log(Level.INFO, "Received reserved type 12 from "+pp.getSourceAddress().getHostName());
                this.protocol.reserved_type12(pp);
                break;
            case Datatype.INT_GENERIC_FILE:
//                LOGGER.log(Level.INFO, "Received generic file from "+pp.getSourceAddress().getHostName());
                this.protocol.generic_file(pp);
                break;
            case Datatype.INT_IMAGE_FILE_JPEG:
//                LOGGER.log(Level.INFO, "Received JPEG image file from "+pp.getSourceAddress().getHostName());
                this.protocol.image_file_jpeg(pp);
                break;
            case Datatype.INT_IMAGE_FILE_PNG:
//                LOGGER.log(Level.INFO, "Received PNG image file from "+pp.getSourceAddress().getHostName());
                this.protocol.image_file_png(pp);
                break;
            case Datatype.INT_IMAGE_FILE_BMP:
//                LOGGER.log(Level.INFO, "Received BMP image file from "+pp.getSourceAddress().getHostName());
                this.protocol.image_file_bmp(pp);
                break;
            case Datatype.INT_IMAGE_FILE_GIF:
//                LOGGER.log(Level.INFO, "Received GIF image file from "+pp.getSourceAddress().getHostName());
                this.protocol.image_file_gif(pp);
                break;
        }
    }
    
    public UDPListener getListener(){
    	return this.listener;
    }
    
    public WHASProtocol getProtocol(){
    	return this.protocol;
    }
}
