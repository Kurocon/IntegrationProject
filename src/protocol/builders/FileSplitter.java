package protocol.builders;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileSplitter {
	private byte[] data;
	private FileBuilder[] packets;
	private int maxSeq;
	private int length;
	public static final int MAX_BYTES_PER_PACKET = 736;

	public FileSplitter() {

	}

	public void setFile(String path) {
		Path newpath = Paths.get(path);
		try {
			this.data = Files.readAllBytes(newpath);
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (this.data != null) {
			this.length = data.length;
			this.maxSeq = (int) ((length / MAX_BYTES_PER_PACKET) + 1);
			this.packets = new FileBuilder[maxSeq];
			for (int i = 0; i < maxSeq - 1; i++) {
				this.packets[i] = new FileBuilder();
				this.packets[i].setCurrentSequenceNumber(i + 1);
				this.packets[i].setMaxSequenceNumber(this.maxSeq);
				this.packets[i].setFilename((String) newpath.getFileName().toString());
				byte[] toSend = new byte[MAX_BYTES_PER_PACKET];
				System.arraycopy(this.data, i * MAX_BYTES_PER_PACKET, toSend, 0, MAX_BYTES_PER_PACKET - 1);
				this.packets[i].setFileData(toSend);
			}
			this.packets[this.maxSeq - 1] = new FileBuilder();
			this.packets[this.maxSeq - 1].setCurrentSequenceNumber(this.maxSeq);
			this.packets[this.maxSeq - 1].setMaxSequenceNumber(this.maxSeq);
			this.packets[this.maxSeq - 1].setFilename((String) newpath.getFileName().toString());
			byte[] toSend = new byte[this.length - (this.maxSeq - 1) * MAX_BYTES_PER_PACKET];
			System.arraycopy(this.data, (this.maxSeq - 1) * MAX_BYTES_PER_PACKET, toSend, 0, this.length - (this.maxSeq - 1) * MAX_BYTES_PER_PACKET - 1);
			this.packets[this.maxSeq - 1].setFileData(toSend);
		}
	}

	public FileBuilder[] getBuilders() {
		return this.packets;
	}
	public int getPacketAmount(){
		return this.maxSeq;
	}
}
