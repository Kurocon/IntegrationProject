package protocol.parsers;

/**
 * Created by kevin on 4/9/14.
 */
public abstract class Parser {

    private byte[] data;

    public Parser(byte[] data){
        this.data = data;
    }

    public byte[] getData(){
        return this.data;
    }

}
