package network;

import protocol.parsers.PacketParser;

public class ChatLogElement implements LogElement<Long, PacketParser>{
	
	private long timestamp;
	private PacketParser parser;

	@Override
	public void setIndex(Long e) {
		this.timestamp = e;
	}

	@Override
	public void setData(PacketParser f) {
		this.parser = f;
		this.timestamp = this.parser.getTimestamp();
	}
	
	public long getTimestamp(){
		return this.timestamp;
	}
	
	public PacketParser getPacket(){
		return this.parser;
	}

	

}
