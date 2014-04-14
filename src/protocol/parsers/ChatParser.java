package protocol.parsers;

public class ChatParser extends Parser{
	private String messageAsString;
	private byte[] messageAsBytes ;
	

	public ChatParser(byte[] data, int length) {
		super(data);
		messageAsBytes = new byte[length];
		System.arraycopy(data, 0, messageAsBytes, 0, length);
		this.messageAsString = new String(messageAsBytes);
        this.messageAsString = this.messageAsString.substring(0,length);
		this.messageAsBytes = data;
	}
	
	public String getMessage(){
		return this.messageAsString;
	}
	
	public byte[] getMessageAsBytes(){
		return this.messageAsBytes;
	}
}
