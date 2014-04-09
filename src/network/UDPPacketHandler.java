package network;

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
        // Print it, it might be important!
        String s = new String(packet.getData());

        LOGGER.log(Level.INFO, "PacketHandler received: " + s);
    }
}
