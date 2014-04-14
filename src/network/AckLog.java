package network;

import protocol.Timestamp;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by kevin on 4/11/14.
 */
public class AckLog implements Log {

    private HashMap<Long, LogElement> log = new HashMap<>();

    @Override
    public void addElement(LogElement e) {
        this.log.put((Long) e.getIndex(), e);
    }

    @Override
    public void removeElement(Long t){
        if(this.log.containsKey(t)){
            this.log.remove(t);
        }
    }

    @Override
    public LogElement getElement(long t) {
        return this.log.get(t);
    }

    @Override
    public LogElement[] getAllElements() {
        LogElement[] res = new LogElement[this.log.keySet().size()];
        int i = 0;
        for(Long t : this.log.keySet()){
            res[i] = this.log.get(t);
            i++;
        }
        return res;
    }
}
