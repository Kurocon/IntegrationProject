package network;

import protocol.parsers.PacketParser;

public class ChatLogElement implements LogElement<Long, String>{
	
	private long timestamp;
	private String msg;
    private User user;

	@Override
	public void setIndex(Long e) {
		this.timestamp = e;
	}

	@Override
	public void setData(String f) {
		this.msg = f;
	}

    public void setUser(User u){
        this.user = u;
    }

    @Override
    public Long getIndex() {
        return this.timestamp;
    }

    @Override
    public String getData() {
        return this.msg;
    }

    public User getUser(){
        return this.user;
    }
}
