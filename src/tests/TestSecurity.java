package tests;

import network.Security;

public class TestSecurity {
	
	public TestSecurity(){
		byte[] msg = "Hallo, dit is een leuke message".getBytes();
		String pw = "ditishetwachtwoord";
        Security s = new Security();
        s.setPassword(pw);
		byte[] encrypted = s.encryptData(msg);
		System.out.println(byteArrayToHex(msg));
		System.out.println(byteArrayToHex(encrypted));
		byte[] decrypted = s.decryptData(encrypted);
		System.out.println(byteArrayToHex(decrypted));
	}

	public static void main(String[] args){
		TestSecurity a = new TestSecurity();
	}
	
	public String byteArrayToHex(byte[] a) {
        StringBuilder sb = new StringBuilder();
        for(byte b: a)
            sb.append(String.format("%02x", b&0xff));
        return sb.toString();
    }

}
