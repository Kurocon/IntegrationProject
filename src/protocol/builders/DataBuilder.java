package protocol.builders;

import exceptions.WrongArrayLengthException;

/**
 * Shell for a databuilder. Can be extended upon for individual data types.
 * Created by kevin on 4/8/14.
 */
public class DataBuilder {

    private byte[] data;
    private byte[] dataType;
    private byte[] dataLength;

    public DataBuilder(){
        this.dataLength = new byte[]{0x00, 0x00};
    }

    public void setDataType(byte[] dataType){
        if(dataType.length != 2){
            throw new WrongArrayLengthException("DataType must be 2 bytes long.");
        }else{
            this.dataType = dataType;
        }
    }

    public byte[] getDataType(){
        return this.dataType;
    }

    public void setDataLength(byte[] dataLength){
        if(dataLength.length != 2){
            throw new WrongArrayLengthException("DataLength must be 2 bytes long.");
        }else{
            this.dataLength = dataLength;
        }
    }

    public byte[] getDataLength(){
        return this.dataLength;
    }

    public void setData(byte[] data){
        this.data = data;
    }

    public byte[] getData(){
        return this.data;
    }
    
    public byte[] concatByteArrays(byte[] a, byte[] b){
        int aLen = a.length;
        int bLen = b.length;
        byte[] c = new byte[aLen+bLen];
        System.arraycopy(a, 0, c, 0, aLen);
        System.arraycopy(b, 0, c, aLen, bLen);
        return c;
    }

}
