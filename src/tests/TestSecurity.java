package tests;

public class TestSecurity {
	
	public TestSecurity(){
		byte[] msg = "Hallo, dit is een leuke message".getBytes();
		byte[] key = new byte[] {0,1,2,3,4,5,6,7,8,9,0,1,2,3,4,5};
		byte[] encrypted = network.Security.encryptData(msg, key);
		System.out.println(byteArrayToHex(msg));
		System.out.println(byteArrayToHex(encrypted));
		byte[] decrypted = network.Security.decryptData(encrypted, key);
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
