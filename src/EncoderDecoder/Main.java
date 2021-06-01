package EncoderDecoder;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc= new Scanner(System.in); //System.in is a standard input stream
        System.out.print("Enter a string: ");
        String str= sc.nextLine();              //reads string
        System.out.print("You have entered: "+str+"\n");

        EncoderDecoder encoderDecoder = new EncoderDecoder();
        encoderDecoder.setData(str);
        String hash = "egp12345";
        String encrypted = encoderDecoder.getSymEncrypt(hash);
        System.out.print("Encrypted: "+encrypted+"\n");

        encoderDecoder.setEncrypt(encrypted);
        String decrypted = encoderDecoder.getSymDecrypt(hash);
        System.out.print("Decrypted: "+decrypted+"\n");
    }
}
