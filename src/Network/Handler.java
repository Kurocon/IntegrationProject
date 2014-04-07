package Network;

import java.net.DatagramPacket;

/**
 * Created by kevin on 4/7/14.
 */
public interface Handler {

    public void handleInput(DatagramPacket packet);

}
