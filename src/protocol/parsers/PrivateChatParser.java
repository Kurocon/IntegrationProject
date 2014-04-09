package protocol.parsers;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class PrivateChatParser extends Parser{
	private String dataAsString;
	private byte[] dataAsBytes;
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
		
		dataAsBytes = new byte[length];
		System.arraycopy(data, 4, dataAsBytes, 0, length);
		this.dataAsString = new String(dataAsBytes);
		this.dataAsBytes = data;
	}
	
	public byte[] getDestinationAsBytes(){
		return this.destination;
	}
	
	public String getDestinationAsString(){
		return this.destinationAsString;
	}
	
	public String getDataAsString(){
		return this.dataAsString;
	}
	
	public byte[] getDataAsBytes(){
		return this.dataAsBytes;
	}
}
