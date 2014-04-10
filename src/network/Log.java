package network;

import protocol.Timestamp;

/**
 * Created by kevin on 4/10/14.
 */
public interface Log<E> {

    public E[] getLog();
    public void addElement(E e);
    public E getElement(Timestamp t);

}
