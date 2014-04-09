package Protocol;

import java.nio.ByteBuffer;

public class ChatBuilder extends DataBuilder{
	
	private byte[] message;
	
	
	public ChatBuilder(){
		super();
		setDataType(Datatype.CHAT_MESSAGE);
	}
	
	public void setMessage(String msg){
		this.message = msg.getBytes();
	}
	
	public byte[] getMessage(){
		return this.message;
	}
	
	public byte[] getData(){
		this.setDataLength(ByteBuffer.allocate(2).putInt(this.message.length));
		return this.getMessage();
	}

}
