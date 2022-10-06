package icu.whereis.somecode.test;

import org.apache.tika.Tika;

import java.io.File;
import java.io.IOException;

public class FileTypeCheckMain {
    public static void main(String[] args) {
        String filePath = "C:\\Users\\Administrator\\Desktop\\qc_151369_sign.aps";
        Tika tika = new Tika();
        String detect = null;
        try {
            // 需要注意传入String和URL，将只检测字符串本身，不会进行真正的文件检测
            detect = tika.detect(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(detect);
    }
}
