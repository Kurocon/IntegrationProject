package protocol;

public class AckBuilder extends DataBuilder{
	
private byte[] AckNumber;

public AckBuilder(){
	super();
	super.setDataType(Datatype.GENERIC_ACK);
	
}

public void setAck(byte[] number){
	this.AckNumber = number;
}

public byte[] getAckNumber(){
	return AckNumber;
}

public byte[] getData(){
	super.setData(this.getAckNumber());
	return super.getData();
}

}
