package icu.whereis.somecode.test;

import icu.whereis.somecode.cipher.AES;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class TestMain {

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        String xx = "C:\\Users\\Administrator\\Desktop\\1.txt";
        File f1 = new File(xx);
        String inputText2 = FileUtils.readFileToString(f1, StandardCharsets.UTF_8);

        String text = args[0];
        //byte[] key = (args.length == 2) ? args[1].getBytes() : getKey();
        byte[] key = "23333555555555554".getBytes();

        // Input prepare stuff
        byte[] inputText = inputText2.getBytes();


        double startTime = System.currentTimeMillis();
        //for (int i=0; i < 100000; i++) cipher.ECB_decrypt(cipher.ECB_encrypt(inputText));

        String xx2 = "C:\\Users\\Administrator\\Desktop\\1.dat";
        File file = new File(xx2);

        IOUtils.write(AES.encrypt(inputText), new FileOutputStream(file));

        byte[] bytes = FileUtils.readFileToByteArray(file);
        String xx3 = "C:\\Users\\Administrator\\Desktop\\2.dat";
        File file3 = new File(xx3);
        IOUtils.write(AES.decrypt(bytes), new FileOutputStream(file3));



    }



    /**
     * 字符串转16进制表示
     * https://stackoverflow.com/questions/923863/converting-a-string-to-hexadecimal-in-java
     */
    public static void toHexString() {
        String x = "十六进制字符串";
        try {
            String s = Hex.encodeHexString(x.getBytes("UTF-8"));
            System.out.println(s);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
