package Protocol;

public class AckBuilder extends DataBuilder{
	
private byte[] AckNumber;

public AckBuilder(){
	super();
	setDataType(Datatype.GENERIC_ACK);
	
}

public void setAck(byte[] number){
	this.AckNumber = number;
}

public byte[] getAckNumber(){
	return AckNumber;
}

public byte[] getData(){
	return this.getAckNumber();
}

}
