package network;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Security {

    private final Logger LOGGER = Logger.getLogger(Security.class.getName());

    private Cipher cipher;
    private SecretKeySpec key;
    private IvParameterSpec ivspec;

    public Security(){
        try {
            this.cipher = Cipher.getInstance("AES/CBC/NoPadding");
        } catch (NoSuchAlgorithmException e) {
            LOGGER.log(Level.SEVERE, "Error building security cipher. ["+e.getMessage()+"]");
        } catch (NoSuchPaddingException e) {
            LOGGER.log(Level.SEVERE, "Error building security cipher. [" + e.getMessage() + "]");
        }

        byte[] iv = new byte[0];
        try {
            iv = MessageDigest.getInstance("MD5").digest("SecurityIV".getBytes());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        this.ivspec = new IvParameterSpec(iv);

    }

    public void setPassword(String pw){
        try {
            byte[] digest = MessageDigest.getInstance("MD5").digest(pw.getBytes());

            this.key = new SecretKeySpec(digest, "AES");

            LOGGER.log(Level.INFO, "Encryption key set.");


        } catch (NoSuchAlgorithmException e) {
            LOGGER.log(Level.INFO, "Could not get MessageDigest instance. ["+e.getMessage()+"]");
        }
    }
	
	public byte[] encryptData(byte[] msg){
		try {
            try {
                this.cipher.init(Cipher.ENCRYPT_MODE, this.key, this.ivspec);
            } catch (InvalidAlgorithmParameterException e) {
                LOGGER.log(Level.SEVERE, "Error building security cipher. [" + e.getMessage() + "]");
            }
            byte[] encryptedData = this.cipher.doFinal(msg);
			return encryptedData;
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
        return null;
	}
	
	public byte[] decryptData(byte[] encryptedData){
		try {
            try {
                this.cipher.init(Cipher.DECRYPT_MODE, this.key, this.ivspec);
            } catch (InvalidAlgorithmParameterException e) {
                LOGGER.log(Level.SEVERE, "Error building security cipher. [" + e.getMessage() + "]");
            }
            byte[] decryptedData = this.cipher.doFinal(encryptedData);
			return decryptedData;
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
        }
        return null;
	}


}
