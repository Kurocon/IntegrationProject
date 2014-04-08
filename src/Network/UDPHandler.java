package Network;

import java.net.DatagramPacket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Packet handler to handle incoming input packets and forwarding them to the right method in the protocol.
 * Created by kevin on 4/7/14.
 */
public class UDPHandler implements Handler{

    private static final Logger LOGGER = Logger.getLogger(UDPServer.class.getName());

    WHASProtocol protocol = null;

    public UDPHandler(){
        LOGGER.log(Level.INFO, "UDPHandler is starting...");
        this.protocol = new WHASProtocol();
        LOGGER.log(Level.INFO, "Ready to handle input from server!");
    }

    @Override
    public void handleInput(DatagramPacket packet) {
        // My god, we received something.
        // Print it, it might be important!
        String s = new String(packet.getData());

        LOGGER.log(Level.INFO, "Handler received: " + s);
    }
}
