package Protocol;

import java.nio.ByteBuffer;

public class PrivateChatBuilder extends DataBuilder{
	
	private byte[] destination;
	private byte[] message;
	
	public PrivateChatBuilder(){
		super();
		this.setDataType(Datatype.PRIVATE_MESSAGE);
	}
	
	public void setDestination(byte[] dest){
		this.destination = dest;
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
		this.setDataLength(ByteBuffer.allocate(2).putInt(this.message.length));
		return concatByteArrays(this.getDestination(), this.getMessage());
	}

}
