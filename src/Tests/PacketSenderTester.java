package Tests;

import OldNetwork.UDPHandler;

/**
 * Created by kevin on 4/7/14.
 */
public class PacketSenderTester {

    public static void main(String[] args){
        PacketSenderTester tester = new PacketSenderTester();
    }

    public PacketSenderTester(){


        UDPHandler h = new UDPHandler("127.0.0.1", 5555);



    }

}
