package network;

import java.net.DatagramPacket;

/**
 * PacketHandler
 * Created by kevin on 4/7/14.
 */
public interface PacketHandler {

    public void handleInput(DatagramPacket packet);

}
