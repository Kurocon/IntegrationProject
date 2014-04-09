package Protocol;

import Exceptions.WrongArrayLengthException;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Shell for a databuilder. Can be extended upon for individual data types.
 * Created by kevin on 4/8/14.
 */
public class DataBuilder {

    private byte[] data;
    private byte[] dataType;

    public void setDataType(byte[] dataType){
        if(dataType.length != 2){
            throw new WrongArrayLengthException("DataType must be 2 bytes long.");
        }else{
            this.dataType = getDataType();
        }
    }

    public byte[] getDataType(){
        return this.dataType;
    }

    public void setData(byte[] data){
        this.data = data;
    }

    public byte[] getData(){
        return this.data;
    }

}
