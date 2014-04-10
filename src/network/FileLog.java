package network;

/**
 * Created by kevin on 4/10/14.
 */
public abstract class FileLog implements Log<File>{

    public void addElement(String filename, byte[] datatype, byte[] data){
        File f = new UDPFile();
        f.setFilename(filename);
        f.setFiletype(datatype);
        f.setData(data);
        this.addElement(f);
    }

}
