package protocol;

import gui.CaUI;
import network.*;
import protocol.parsers.*;
import network.UDPPacketHandler;
import network.User;
import sampca.SAMPCA;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Protocol implementation
 * Created by kevin on 4/7/14.
 */
public class WHASProtocol implements Protocol {

    private UDPPacketHandler handler = null;
    private static final Logger LOGGER = Logger.getLogger(WHASProtocol.class.getName());

    private FilePacketParser fileParser = new FilePacketParser();

    public WHASProtocol(UDPPacketHandler h){
        LOGGER.setLevel(SAMPCA.GLOBAL_LOGGER_LEVEL);
        this.handler = h;
    }

    /*
            SYSTEM AND CHAT HANDLERS
     */

    @Override
    public void generic_ack(PacketParser data) {
        AckParser ap = new AckParser(data.getData());
        long ackCode = ap.getAck();
        AckLogElement ale = (AckLogElement) this.handler.getListener().getSAMPCA().getAckLog().getElement(ackCode);
        if(ale != null) {
            ale.setAck(data.getSourceAddress(), true);
        }
    }

    @Override
    public void broadcast_message(PacketParser data) {
    	BroadcastMessageParser bmp = new BroadcastMessageParser(data.getData());
    	User u = this.handler.getListener().getSAMPCA().getUser(data.getSourceAddress());
        if(u == null){
            u = new UDPUser();
            u.setName(bmp.getNick());
            u.setHostname(bmp.getHost());
            u.setIP(data.getSourceAddress());
            u.setPort(this.handler.getListener().getSAMPCA().getPort());
        }
        u.setLastSeen(Timestamp.getCurrentTimeAsLong());
        this.handler.getListener().getSAMPCA().addUser(u);
    }

    @Override
    public void chat_message(PacketParser data) {
    	ChatParser cp = new ChatParser(data.getData(), data.getDataLength());
        CaUI chatGui = this.handler.getListener().getSAMPCA().getChatGUI();
        if(chatGui != null) {
            chatGui.addMessage(data.getSourceAddress(), data.getDestinationAddress(), cp.getMessage(), data.getTimestamp(), false);
        }
    }

    @Override
    public void private_message(PacketParser data) {
      	PrivateChatParser cp = new PrivateChatParser(data.getData(), data.getDataLength());
        CaUI chatGui = this.handler.getListener().getSAMPCA().getChatGUI();
        if(chatGui != null) {
            chatGui.addMessage(data.getSourceAddress(), data.getDestinationAddress(), cp.getMessageAsString(), data.getTimestamp(), true);
        }
    }

    /*
            RESERVED TYPE HANDLERS
     */

    @Override
    public void reserved_type1(PacketParser data) {

    }

    @Override
    public void reserved_type2(PacketParser data) {

    }

    @Override
    public void reserved_type3(PacketParser data) {

    }

    @Override
    public void reserved_type4(PacketParser data) {

    }

    @Override
    public void reserved_type5(PacketParser data) {

    }

    @Override
    public void reserved_type6(PacketParser data) {

    }

    @Override
    public void reserved_type7(PacketParser data) {

    }

    @Override
    public void reserved_type8(PacketParser data) {

    }

    @Override
    public void reserved_type9(PacketParser data) {

    }

    @Override
    public void reserved_type10(PacketParser data) {

    }

    @Override
    public void reserved_type11(PacketParser data) {

    }

    @Override
    public void reserved_type12(PacketParser data) {

    }

    /*
            FILE HANDLERS
     */

    @Override
    public void generic_file(PacketParser data) {
        fileParser.addFilePacket(data);
        FileParser fp = new FileParser(data.getData(), data.getDataLength());
        CaUI chatGui = this.handler.getListener().getSAMPCA().getChatGUI();
        if(chatGui != null) {
        	String status = "Received file part "+fp.getCurrentPacketNumber()+"/"+fp.getTotalPacketsNumber();
        	chatGui.addTransferMessage(data.getSourceAddress(), data.getDestinationAddress(), status, data.getTimestamp(), true);
        }
        if(fileParser.isFileComplete()){
            fileParser.saveFile();
            LOGGER.log(Level.INFO, "Received file from "+data.getSourceAddress()+", saved in "+fileParser.getSavedFilePath());
            if(chatGui != null) {
                chatGui.addTransferMessage(data.getSourceAddress(), data.getDestinationAddress(), fileParser.getSavedFilePath(), data.getTimestamp(), false);
            }
            fileParser.removeFile();
        }
    }

    @Override
    public void image_file_jpeg(PacketParser data) {

    }

    @Override
    public void image_file_png(PacketParser data) {

    }

    @Override
    public void image_file_bmp(PacketParser data) {

    }

    @Override
    public void image_file_gif(PacketParser data) {

    }
}
