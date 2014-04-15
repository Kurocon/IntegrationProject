package protocol.builders;

import java.nio.ByteBuffer;

import protocol.Datatype;

public class FileBuilder extends DataBuilder{
	private byte[] currentSequenceNumber;
	private byte[] maxSequenceNumber;
	private byte[] filename = new byte[255];
	private byte[] filenameLength;
	private byte[] filedata;
	
	public FileBuilder(){
		super();
		super.setDataType(Datatype.GENERIC_FILE);
	}
	
	public void setCurrentSequenceNumber(int i){
		this.currentSequenceNumber = ByteBuffer.allocate(4).putInt(i).array();	
	}
	
	public void setMaxSequenceNumber(int i){
		this.maxSequenceNumber = ByteBuffer.allocate(4).putInt(i).array();	
	}	
	public void setFilename(String name){
	this.filenameLength = new byte[]{(byte) name.length()};
	System.arraycopy(name.getBytes(), 0, this.filename, 0, name.length());
	}
	
	public void setFileData(byte[] data){
		this.filedata = data;
	}
	
	public byte[] getData(){
		byte[] fullData;
		fullData = concatByteArrays(this.getCurrentSequenceNumber(), this.getMaxSequenceNumber());
		fullData = concatByteArrays(fullData, this.getFilename());
		fullData = concatByteArrays(fullData, this.getFilenameLength());
		fullData = concatByteArrays(fullData, this.getFileData());
		super.setDataLength(ByteBuffer.allocate(2).putShort((short) fullData.length).array());
		super.setData(fullData);
		return super.getData();
	}

	private byte[] getFileData() {
		return this.filedata;
	}

	private byte[] getFilenameLength() {
		return this.filenameLength;
	}

	private byte[] getMaxSequenceNumber() {
		return this.maxSequenceNumber;
	}

	private byte[] getCurrentSequenceNumber() {
		return this.currentSequenceNumber;
	}

	private byte[] getFilename() {
		return this.filename;
	}
}
