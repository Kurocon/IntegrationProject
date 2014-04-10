package network;

import org.omg.CORBA.TIMEOUT;
import protocol.BroadcastMessageBuilder;
import protocol.PacketBuilder;
import protocol.Timestamp;
import sampca.SAMPCA;

import java.util.LinkedList;
import java.util.TimerTask;

/**
 * Created by kevin on 4/10/14.
 */
public class TimeoutTimerTask extends TimerTask {

    SAMPCA sampca;

    public TimeoutTimerTask(SAMPCA s){
        this.sampca = s;
    }

    @Override
    public void run() {

        // Remove timed-out users.
        LinkedList<User> users = this.sampca.getUsers();
        for(User u : users){
            if(u.getLastSeen() < Timestamp.getCurrentTimeAsLong() - 35000){
                // Too long, timeout.
                this.sampca.removeUser(u);
            }
        }

        // Check for timed out ACKs and resend messages accordingly

        // Send a broadcast to let others know we are still here
        this.sampca.sendBroadcastMessage();

    }
}
