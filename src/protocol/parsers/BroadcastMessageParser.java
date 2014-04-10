package protocol.parsers;


public class BroadcastMessageParser extends Parser{

	private byte[] nickAsBytes;
	private byte[] hostAsBytes;
	private String nickAsString;
	private String hostAsString;
	private int nickLength;
	private int hostLength;
	
	public BroadcastMessageParser(byte[] data){
		super(data);
		this.nickLength = data[510];
		this.hostLength = data[511];

		System.arraycopy(data, 0, this.nickAsBytes, 0, this.nickLength);
		this.nickAsString = new String(this.nickAsBytes);
		
		System.arraycopy(data, 255, this.hostAsBytes, 0, this.hostLength);
		this.hostAsString = new String(this.hostAsBytes);
	
	}
	
	public String getNick(){
		return this.nickAsString; 
	}
	
	public String getHost(){
		return this.hostAsString;
	}
	
	public byte[] getNickAsBytes(){
		return this.nickAsBytes;
	}
	
	public byte[] getHostAsBytes(){
		return this.getHostAsBytes();
	}
	
	public int getNickLength(){
		return this.nickLength;
	}
	
	public int getHostLength(){
		return this.hostLength;
	}
}
