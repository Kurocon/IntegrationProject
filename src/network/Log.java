package network;


/**
 * Created by kevin on 4/10/14.
 */
public interface Log {

    public void addElement(LogElement e);
    public LogElement getElement(long t);
    public LogElement[] getAllElements();

}
