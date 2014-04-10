package network;

/**
 * Created by kevin on 4/10/14.
 */
public interface File {
    public void setFilename(String name);
    public void setData(byte[] data);
    public void setFiletype(byte[] filetype);

    public String getFilename();
    public byte[] getData();
    public byte[] getFiletype();
}
