package protocol.parsers;

import java.nio.ByteBuffer;

public class AckParser extends Parser{
	private byte[] ackAsBytes = new byte[4];
	private long ackAsLong;

	public AckParser(byte[] data) {
		super(data);
		System.arraycopy(data, 0, this.ackAsBytes, 0, 4);
		this.ackAsLong = ByteBuffer.allocate(8).put(this.ackAsBytes).getLong();
		
	}
	
	public byte[] getAckAsBytes(){
		return this.ackAsBytes;
	}
	
	public long getAck(){
		return ackAsLong;
	}
	

}
