package protocol.parsers;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class PrivateChatParser extends Parser{
	private String messageAsString;
	private byte[] messageAsBytes;
	private byte[] destination;
	private String destinationAsString;

	public PrivateChatParser(byte[] data, int length) {
		super(data);
		destination = new byte[4];
		System.arraycopy(data, 0, destination, 0, 4);
		try {
			destinationAsString = InetAddress.getByAddress(destination).getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		messageAsBytes = new byte[length];
		System.arraycopy(data, 4, messageAsBytes, 0, length);
		this.messageAsString = new String(messageAsBytes);
		this.messageAsBytes = data;
	}
	
	public byte[] getDestinationAsBytes(){
		return this.destination;
	}
	
	public String getDestinationAsString(){
		return this.destinationAsString;
	}
	
	public String getMessageAsString(){
		return this.messageAsString;
	}
	
	public byte[] getMessageAsBytes(){
		return this.messageAsBytes;
	}
}
