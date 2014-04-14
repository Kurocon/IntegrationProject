package protocol.builders;

import protocol.Datatype;

import java.net.InetAddress;
import java.nio.ByteBuffer;

public class PrivateChatBuilder extends DataBuilder{
	
	private byte[] destination;
	private byte[] message;
	
	public PrivateChatBuilder(){
		super();
		super.setDataType(Datatype.PRIVATE_MESSAGE);
	}
	
	public void setDestination(InetAddress dest){
		this.destination = dest.getAddress();
	}
	
	public byte[] getDestination(){
		return this.destination;
	}
	
	public void setMessage(String msg){
		this.message = msg.getBytes();
	}
	
	public byte[] getMessage(){
		return this.message;
	}
	
	public byte[] getData(){
		super.setDataLength(ByteBuffer.allocate(2).putShort((short) this.message.length).array());
		super.setData(concatByteArrays(this.getDestination(), this.getMessage()));
		return super.getData();
		

	}

}
