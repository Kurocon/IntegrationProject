package protocol;

import java.nio.ByteBuffer;

public class ChatBuilder extends DataBuilder{
	
	private byte[] message;
	
	
	public ChatBuilder(){
		super();
		super.setDataType(Datatype.CHAT_MESSAGE);
	}
	
	public void setMessage(String msg){
		this.message = msg.getBytes();
	}
	
	public byte[] getMessage(){
		return this.message;
	}
	
	public byte[] getData(){
		super.setDataLength(ByteBuffer.allocate(2).putInt(this.message.length).array());
		super.setData(this.getMessage());
		return super.getData();
	}

}
