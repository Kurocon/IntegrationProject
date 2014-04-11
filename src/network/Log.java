package network;

import protocol.Timestamp;

/**
 * Created by kevin on 4/10/14.
 */
public interface Log {

    public void addElement(LogElement e);
    public LogElement getElement(Timestamp t);
    public LogElement[] getAllElements();

}
