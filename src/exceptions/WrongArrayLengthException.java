package exceptions;

/**
 * Created by kevin on 4/8/14.
 */
public class WrongArrayLengthException extends IllegalArgumentException {

    public WrongArrayLengthException(String msg){
        super(msg);
    }

}
