package network;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class Security {

    private final Logger LOGGER = Logger.getLogger(Security.class.getName());

    private Cipher cipher;
    private SecretKeySpec key;

    public Security(){
        try {
            this.cipher = Cipher.getInstance("AES");
        } catch (NoSuchAlgorithmException e) {
            LOGGER.log(Level.SEVERE, "Error building security cipher. ["+e.getMessage()+"]");
        } catch (NoSuchPaddingException e) {
            LOGGER.log(Level.SEVERE, "Error building security cipher. [" + e.getMessage() + "]");
        }
    }

    public void setPassword(String pw){
        byte[] key = new byte[16];
        if(pw.getBytes().length >= 16){
            System.arraycopy(pw.getBytes(), 0, key, 0, 16);
        }else{
            System.arraycopy(pw.getBytes(), 0, key, 0, pw.length());
        }
        this.key = new SecretKeySpec(key, "AES");
        LOGGER.log(Level.INFO, "Encryption key set to password.");
    }
	
	public byte[] encryptData(byte[] msg){
		try {
			this.cipher.init(Cipher.ENCRYPT_MODE, this.key);
			byte[] encryptedData = this.cipher.doFinal(msg);
			LOGGER.log(Level.INFO, "Encrypting packet");

			return encryptedData;
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}finally {
            return null;
        }
	}
	
	public byte[] decryptData(byte[] encryptedData){
		try {
			this.cipher.init(Cipher.DECRYPT_MODE, this.key);
			byte[] decryptedData = this.cipher.doFinal(encryptedData);
            LOGGER.log(Level.INFO, "Decrypting packet");
			return decryptedData;
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} finally {
            return null;
        }
	}


}
