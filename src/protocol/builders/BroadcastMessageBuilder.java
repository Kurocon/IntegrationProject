package protocol.builders;

import protocol.Datatype;

import java.nio.ByteBuffer;

public class BroadcastMessageBuilder extends DataBuilder{
	private byte[] nickname;
	private byte[] hostname;
	
	public BroadcastMessageBuilder(){
		super();
		super.setDataType(Datatype.BROADCAST_MESSAGE);
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

        byte[] nick = new byte[255];
        byte[] host = new byte[255];
        byte[] nickLength = new byte[]{(byte) this.getNick().length};
        byte[] hostLength = new byte[]{(byte) this.getHostname().length};

        System.arraycopy(this.getNick(), 0, nick, 0, this.getNick().length);
        System.arraycopy(this.getHostname(), 0, host, 0, this.getHostname().length);

        byte[] nickhost = concatByteArrays(nick, host);
        byte[] lengths = concatByteArrays(nickLength, hostLength);

		super.setData(concatByteArrays(nickhost, lengths));
		return super.getData();
	}
}
