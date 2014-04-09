package protocol;

import java.nio.ByteBuffer;

/**
 * Default datatypes to use in packets
 * Created by kevin on 4/8/14.
 */
public class Datatype {
    /*
        Reserved datatypes.
     */
    public static final byte[] GENERIC_ACK          = new byte[]{0x00, 0x00}; // 0x0000
    public static final byte[] BROADCAST_MESSAGE    = new byte[]{0x00, 0x01}; // 0x0001
    public static final byte[] CHAT_MESSAGE         = new byte[]{0x00, 0x02}; // 0x0002
    public static final byte[] PRIVATE_MESSAGE      = new byte[]{0x00, 0x03}; // 0x0003
    public static final byte[] RESERVED_TYPE_1      = new byte[]{0x00, 0x04}; // 0x0004
    public static final byte[] RESERVED_TYPE_2      = new byte[]{0x00, 0x05}; // 0x0005
    public static final byte[] RESERVED_TYPE_3      = new byte[]{0x00, 0x06}; // 0x0006
    public static final byte[] RESERVED_TYPE_4      = new byte[]{0x00, 0x07}; // 0x0007
    public static final byte[] RESERVED_TYPE_5      = new byte[]{0x00, 0x08}; // 0x0008
    public static final byte[] RESERVED_TYPE_6      = new byte[]{0x00, 0x09}; // 0x0009
    public static final byte[] RESERVED_TYPE_7      = new byte[]{0x00, 0x0A}; // 0x000A
    public static final byte[] RESERVED_TYPE_8      = new byte[]{0x00, 0x0B}; // 0x000B
    public static final byte[] RESERVED_TYPE_9      = new byte[]{0x00, 0x0C}; // 0x000C
    public static final byte[] RESERVED_TYPE_10     = new byte[]{0x00, 0x0D}; // 0x000D
    public static final byte[] RESERVED_TYPE_11     = new byte[]{0x00, 0x0E}; // 0x000E
    public static final byte[] RESERVED_TYPE_12     = new byte[]{0x00, 0x0F}; // 0x000F

    /*
        Different kinds of files (Mime-types)
     */
    public static final byte[] GENERIC_FILE         =   new byte[]{0x00, 0x10};
    public static final byte[] IMAGE_FILE_JPEG      =   new byte[]{0x00, 0x11};
    public static final byte[] IMAGE_FILE_PNG       =   new byte[]{0x00, 0x12};
    public static final byte[] IMAGE_FILE_BMP       =   new byte[]{0x00, 0x13};
    public static final byte[] IMAGE_FILE_GIF       =   new byte[]{0x00, 0x14};


    // AS INTEGER
    /*
        Reserved datatypes.
     */
    public static final int INT_GENERIC_ACK          = 0; // 0x0000
    public static final int INT_BROADCAST_MESSAGE    = 1; // 0x0001
    public static final int INT_CHAT_MESSAGE         = 2; // 0x0002
    public static final int INT_PRIVATE_MESSAGE      = 3; // 0x0003
    public static final int INT_RESERVED_TYPE_1      = 4; // 0x0004
    public static final int INT_RESERVED_TYPE_2      = 5; // 0x0005
    public static final int INT_RESERVED_TYPE_3      = 6; // 0x0006
    public static final int INT_RESERVED_TYPE_4      = 7; // 0x0007
    public static final int INT_RESERVED_TYPE_5      = 8; // 0x0008
    public static final int INT_RESERVED_TYPE_6      = 9; // 0x0009
    public static final int INT_RESERVED_TYPE_7      = 10; // 0x000A
    public static final int INT_RESERVED_TYPE_8      = 11; // 0x000B
    public static final int INT_RESERVED_TYPE_9      = 12; // 0x000C
    public static final int INT_RESERVED_TYPE_10     = 13; // 0x000D
    public static final int INT_RESERVED_TYPE_11     = 14; // 0x000E
    public static final int INT_RESERVED_TYPE_12     = 15; // 0x000F

    /*
        Different kinds of files (Mime-types)
     */
    public static final int INT_GENERIC_FILE         =   16;
    public static final int INT_IMAGE_FILE_JPEG      =   17;
    public static final int INT_IMAGE_FILE_PNG       =   18;
    public static final int INT_IMAGE_FILE_BMP       =   19;
    public static final int INT_IMAGE_FILE_GIF       =   20;

    public static int getDataTypeAsInt(byte[] dt){
        return ByteBuffer.allocate(2).put(dt).getInt();
    }
}