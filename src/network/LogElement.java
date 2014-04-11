package network;

/**
 * Created by kevin on 4/11/14.
 */
public interface LogElement<E, F> {

    public void setIndex(E e);
    public void setData(F f);

    public E getIndex();
    public F getData();
}
