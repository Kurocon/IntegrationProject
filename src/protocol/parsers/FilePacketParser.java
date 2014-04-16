package protocol.parsers;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * FilePacketParser
 * Created by kevin on 4/15/14.
 */
public class FilePacketParser {

    private static final Logger LOGGER = Logger.getLogger(FilePacketParser.class.getName());

    private String fileName;
    private HashMap<Integer, byte[]> fileMap = new HashMap<>();
    private int fileLength;
    private int blockLength;
    private String savedFilePath;

    public void addFilePacket(PacketParser data) {
        FileParser fp = new FileParser(data.getData(), data.getDataLength());
        this.fileName = fp.getFileName();
        this.fileLength = fp.getTotalPacketsNumber();
        this.fileMap.put(fp.getCurrentPacketNumber(), fp.getFileData());
        this.blockLength = fp.getFileData().length;
        LOGGER.log(Level.INFO, "Received file part "+fp.getCurrentPacketNumber()+"/"+fp.getTotalPacketsNumber());
    }

    public boolean isFileComplete() {
        boolean complete = true;
        for(int i = 1; i <= this.fileLength; i++){
            if(!this.fileMap.containsKey(i)){
                complete = false;
            }
        }
        return complete;
    }

    public void saveFile(){
        int lastPacketLength = this.fileMap.get(fileLength).length;
        int totalLength = ((this.fileLength * this.blockLength) - (this.blockLength-lastPacketLength));
        ByteBuffer fileBuffer = ByteBuffer.allocate(totalLength);
        for(int i = 1; i <= this.fileLength; i++){
            fileBuffer.put(this.fileMap.get(i));
        }

        try {
            Files.write(Paths.get("/tmp/"+fileName), fileBuffer.array());
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Error while writing output file. ["+e.getMessage()+"]");
        }

        this.savedFilePath = "/tmp/"+fileName;
    }

    public String getSavedFilePath(){
        return this.savedFilePath;
    }

    public void removeFile() {
        this.fileMap = new HashMap<>();
        this.fileName = "";
        this.fileLength = -1;
        this.blockLength = -1;
        this.savedFilePath = "";
    }
    
    public int getProgress(){
        int complete = 0;
        for(int i = 1; i <= this.fileLength; i++){
            if(this.fileMap.containsKey(i)){
                complete++;
            }
        }
        double status = ((double) complete / (double) this.fileLength) * 100;
        
        return (int) status;
    }
}
