package Protocol;

public class BroadcastMessageBuilder extends DataBuilder{
	private byte[] nickname;
	private byte[] hostname;
	
	public BroadcastMessageBuilder(){
		super();
		this.setDataType(Datatype.BROADCAST_MESSAGE);
	}
	
	public void setNick(String nick){
		this.nickname = nick.getBytes();
	}
	
	public byte[] getNick(){
		return this.nickname;
	}
	
	public void setHostname(String host){
		this.hostname = host.getBytes();
	}
	
	public byte[] getHostname(){
		return this.hostname;
	}
	
	public byte[] getData(){
		return concatByteArrays(getNick(), getData());
	}
}
