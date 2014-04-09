package protocol.parsers;

public class ChatParser extends Parser{
	private String dataAsString;
	private byte[] dataAsBytes ;

	public ChatParser(byte[] data, int length) {
		super(data);
		dataAsBytes = new byte[length];
		System.arraycopy(data, 0, dataAsBytes, 0, length);
		this.dataAsString = new String(dataAsBytes);
		this.dataAsBytes = data;
	}
	
	public String getDataAsString(){
		return this.dataAsString;
	}
	
	public byte[] getDataAsBytes(){
		return this.dataAsBytes;
	}
}
