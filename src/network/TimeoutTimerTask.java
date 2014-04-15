package network;

import protocol.Timestamp;
import protocol.parsers.PacketParser;
import sampca.SAMPCA;

import java.util.LinkedList;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by kevin on 4/10/14.
 */
public class TimeoutTimerTask extends TimerTask {

    private static final Logger LOGGER = Logger.getLogger(TimeoutTimerTask.class.getName());
    private SAMPCA sampca;

    public TimeoutTimerTask(SAMPCA s){
        this.sampca = s;
        LOGGER.setLevel(SAMPCA.GLOBAL_LOGGER_LEVEL);
        //LOGGER.setLevel(Level.INFO);
    }

    @Override
    public void run() {

        // Remove timed-out users.
        LinkedList<User> users = this.sampca.getUsers();
        LinkedList<User> userCopy = new LinkedList<>();
        for(User u : users){
            userCopy.add(u);
        }
        for(User u : userCopy){
            // Update time for main chat user
            if(u.getIP().equals(this.sampca.getMulticastAddress())){
                LOGGER.log(Level.INFO, "Updating time for main channel.");
                this.sampca.addUser(u);
            }

            if(u.getLastSeen() < Timestamp.getCurrentTimeAsLong() - 62000){
                // Too long, timeout.
                LOGGER.log(Level.WARNING, "Removing user "+u.getName()+". Reason: Timeout (>35s). Last seen: "+u.getLastSeen()+", Current time: "+Timestamp.getCurrentTimeAsLong());
                this.sampca.removeUser(u);
            }
        }

        // Check for timed out ACKs and resend messages accordingly
        // Check ack log.
        for(LogElement e : this.sampca.getAckLog().getAllElements()){
            AckLogElement ale = (AckLogElement) e;

            boolean needsResending = false;
            for(boolean b : ale.getAcks()){
                if(!b){
                    needsResending = true;
                }
            }

            long currentTime = Timestamp.getCurrentTimeAsLong();
            long ackTime = ale.getIndex();

            if(needsResending) {
                LOGGER.log(Level.INFO, "Packet " + ackTime + " time since sent: " + ((currentTime - ackTime) / 1000) + "s");

                if (ackTime < currentTime - 14800) {
                    // Timeout, retransmit message.
                    LOGGER.log(Level.INFO, "Re-transmitting packet " + ackTime + ". Reason: Packet timeout: " + ((currentTime - ackTime)) / 1000 + "s");
                    PacketParser pp = ale.getData();
                    this.sampca.forwardPacket(pp);
                }
            }
            if(ackTime < currentTime - 180000){
                // Packet timeout, remove from queue
                LOGGER.log(Level.WARNING, "Removing element "+ackTime+" from AckLog. Reason: Packet timeout: "+((currentTime-ackTime))/1000+"s");
                this.sampca.getAckLog().removeElement(ackTime);
            }
        }

        // Send a broadcast to let others know we are still here
        this.sampca.sendBroadcastMessage();

    }
}
