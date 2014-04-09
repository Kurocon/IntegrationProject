package protocol;

/**
 * Timestamp class
 * Created by kevin on 4/9/14.
 */
public class Timestamp {
    public static long getCurrentTimeAsLong(){
        return System.currentTimeMillis();
    }

    public static int getCurrentTimeAsInt(){
        return Timestamp.safeLongToInt(Timestamp.getCurrentTimeAsLong());
    }

    public static int safeLongToInt(long l) {
        if (l < Integer.MIN_VALUE || l > Integer.MAX_VALUE) {
            throw new IllegalArgumentException
                    (l + " cannot be cast to int without changing its value.");
        }
        return (int) l;
    }
}
