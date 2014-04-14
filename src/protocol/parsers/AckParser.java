package protocol.parsers;

import java.nio.ByteBuffer;

public class AckParser extends Parser{
	private byte[] ackAsBytes = new byte[8];
	private long ackAsLong;

	public AckParser(byte[] data) {
		super(data);
		System.arraycopy(data, 0, this.ackAsBytes, 0, 8);
		ByteBuffer bb = ByteBuffer.allocate(8).put(this.ackAsBytes);
        bb.position(0);
        this.ackAsLong = bb.getLong();
	}
	
	public byte[] getAckAsBytes(){
		return this.ackAsBytes;
	}
	
	public long getAck(){
		return ackAsLong;
	}
	

}
