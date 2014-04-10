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

        byte[] tempNickAsBytes = new byte[255];
        byte[] tempHostAsBytes = new byte[255];

		System.arraycopy(data, 0, tempNickAsBytes, 0, this.nickLength);
		System.arraycopy(data, 255, tempHostAsBytes, 0, this.hostLength);

        int i;
        for(i=0; i < tempNickAsBytes.length; i++){
            if(tempNickAsBytes[i] == 0x00){
                break;
            }
        }

        int j;
        for(j=0; j < tempHostAsBytes.length; j++){
            if(tempHostAsBytes[j] == 0x00){
                break;
            }
        }

        this.nickAsBytes = new byte[i];
        this.hostAsBytes = new byte[j];

        System.arraycopy(data,   0, this.nickAsBytes, 0, i);
        System.arraycopy(data, 255, this.hostAsBytes, 0, j);

        this.nickAsString = new String(this.nickAsBytes);
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
