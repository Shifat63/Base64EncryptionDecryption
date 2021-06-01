/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package EncoderDecoder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.AccessController;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.PrivilegedAction;
import java.security.spec.InvalidKeySpecException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class EncoderDecoder {

    private String data = null;
    private String encrypt = null;
    String password;

    public String getSymEncrypt(String password2) {
        this.password = password2;
        //String encrypted = "";

        String encrypted = AccessController.doPrivileged(new PrivilegedAction<String>() {

            @Override
            public String run() {
                try {
                    char[] bpassword = password.toCharArray();
                    byte[] salt = new byte[8];
                    Random r = new Random();
                    r.nextBytes(salt);
                    PBEKeySpec kspec = new PBEKeySpec(bpassword);
                    SecretKeyFactory kfact = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
                    SecretKey key = kfact.generateSecret(kspec);
                    PBEParameterSpec pspec = new PBEParameterSpec(salt, 1000);
                    Cipher cipher = Cipher.getInstance("PBEWithMD5AndDES");
                    cipher.init(Cipher.ENCRYPT_MODE, key, pspec);
                    byte[] enc = cipher.doFinal(getData().getBytes());
                    BASE64Encoder encoder = new BASE64Encoder();
                    String saltString = encoder.encode(salt);
                    String ciphertextString = encoder.encode(enc);
                    return saltString + ciphertextString;
                } catch (IllegalBlockSizeException ex) {
                    System.out.println(ex);
                } catch (BadPaddingException ex) {
                    System.out.println(ex);
                } catch (InvalidKeyException ex) {
                    System.out.println(ex);
                } catch (InvalidAlgorithmParameterException ex) {
                    System.out.println(ex);
                } catch (NoSuchPaddingException ex) {
                    System.out.println(ex);
                } catch (InvalidKeySpecException ex) {
                    System.out.println(ex);
                } catch (NoSuchAlgorithmException ex) {
                    System.out.println(ex);}
//                } catch (UnsupportedEncodingException ex) {
//                    System.out.println(ex);
//                }
                return null;
            }
        });
        return encrypted;
    }

    /**
     * Get Symmetric Decrypted String
     * @param password2
     * @return Decrypt String
     */
    public String getSymDecrypt(String password2) {
        this.password = password2;
        String decrypted = AccessController.doPrivileged(new PrivilegedAction<String>() {

            @Override
            public String run() {
                try {
                    String salt = getEncrypt().substring(0, 12);
                    String ciphertext = getEncrypt().substring(12, getEncrypt().length());
                    BASE64Decoder decoder = new BASE64Decoder();
                    byte[] saltArray = decoder.decodeBuffer(salt);
                    byte[] ciphertextArray = decoder.decodeBuffer(ciphertext);
                    PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray());
                    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
                    SecretKey key = keyFactory.generateSecret(keySpec);
                    PBEParameterSpec paramSpec = new PBEParameterSpec(saltArray, 1000);
                    Cipher cipher = Cipher.getInstance("PBEWithMD5AndDES");
                    cipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
                    byte[] plaintextArray = cipher.doFinal(ciphertextArray);
                    return new String(plaintextArray);
                } catch (IllegalBlockSizeException ex) {
                    System.out.println(ex);
                } catch (BadPaddingException ex) {
                    System.out.println(ex);
                } catch (InvalidKeyException ex) {
                    System.out.println(ex);
                } catch (InvalidAlgorithmParameterException ex) {
                    System.out.println(ex);
                } catch (NoSuchPaddingException ex) {
                    System.out.println(ex);
                } catch (InvalidKeySpecException ex) {
                    System.out.println(ex);
                } catch (NoSuchAlgorithmException ex) {
                    System.out.println(ex);
                } catch (IOException ex) {
                    System.out.println(ex);
                }
                return null;
            }
        });
        return decrypted;
    }

    /**
     * @return the data
     */
    public String getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     * @return the encrypt
     */
    public String getEncrypt() {
        return encrypt;
    }

    /**
     * @param encrypt the encrypt to set
     */
    public void setEncrypt(String encrypt) {
        this.encrypt = encrypt;
    }
}
