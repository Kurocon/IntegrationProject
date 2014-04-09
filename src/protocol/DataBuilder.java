package protocol;

import Exceptions.ArrayTooLongException;
import Exceptions.WrongArrayLengthException;

/**
 * Shell for a databuilder. Can be extended upon for individual data types.
 * Created by kevin on 4/8/14.
 */
public class DataBuilder {

    private byte[] data;
    private byte[] dataType;
    private byte[] dataLength = new byte[]{0x00,0x00};

    public void setDataType(byte[] dataType){
        if(dataType.length != 2){
            throw new WrongArrayLengthException("DataType must be 2 bytes long.");
        }else{
            this.dataType = dataType;
        }
    }

    public void setDataLength(byte[] dataLength){
        if(dataLength.length != 2){
            throw new WrongArrayLengthException("DataLength must be 2 bytes long.");
        }else{
            this.dataLength = dataLength;
        }
    }

    public byte[] getDataType(){
        return this.dataType;
    }

    public byte[] getDataLength(){
        return this.dataLength;
    }

    public void setData(byte[] data){
        if(data.length <= 1000){
            this.data = data;
        }else{
            throw new ArrayTooLongException("Data must be shorter then 1000 bytes.");
        }
    }

    public byte[] getData(){
        return this.data;
    }

}
