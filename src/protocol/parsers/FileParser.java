package protocol.parsers;

import sampca.SAMPCA;

import java.nio.ByteBuffer;

/**
 * FileParser
 * Created by kevin on 4/15/14.
 */
public class FileParser extends Parser {

    private int currentPacket;
    private int totalPackets;
    private byte[] fileData;
    private String fileNameAsString;
    private byte[] fileNameAsBytes;
    private int filenameLength;

//    CURRENT_SEQ_NUMBER -- 4 bytes
//    MAX_SEQ_NUMBER -- 4 bytes
//    FILENAME -- 255 bytes
//    FILENAME_LENGTH -- 1 byte

    public FileParser(byte[] data) {
        super(data);

        byte[] curSecNrBytes = new byte[4];
        byte[] maxSecNrBytes = new byte[4];
        System.arraycopy(data, 0, curSecNrBytes, 0, 4);
        System.arraycopy(data, 4, maxSecNrBytes, 0, 4);
        ByteBuffer curSecNrBuffer = ByteBuffer.allocate(4).put(curSecNrBytes);
        ByteBuffer maxSecNrBuffer = ByteBuffer.allocate(4).put(maxSecNrBytes);
        curSecNrBuffer.position(0);
        maxSecNrBuffer.position(0);
        this.currentPacket = curSecNrBuffer.getInt();
        this.totalPackets = maxSecNrBuffer.getInt();
        this.filenameLength = data[263];
        this.fileNameAsBytes = new byte[this.filenameLength];
        System.arraycopy(data, 8, this.fileNameAsBytes, 0, this.filenameLength);
        this.fileNameAsString = new String(this.fileNameAsBytes);

        this.fileData = new byte[SAMPCA.MAX_BYTES_PER_FILE_PACKET];
        System.arraycopy(data, 264, fileData, 0, SAMPCA.MAX_BYTES_PER_FILE_PACKET);
    }

    public byte[] getFileData(){
        return this.fileData;
    }

    public int getCurrentPacketNumber(){
        return this.currentPacket;
    }

    public int getTotalPacketsNumber(){
        return this.totalPackets;
    }

    public String getFileName(){
        return this.fileNameAsString;
    }


}
