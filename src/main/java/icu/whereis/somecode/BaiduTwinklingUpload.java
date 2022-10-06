package icu.whereis.somecode;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * 百度本地文件秒传链接生成
 * 参考：https://github.com/userElaina/miao-chuan
 */
public class BaiduTwinklingUpload {

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        String filePath = "C:\\Users\\Administrator\\Desktop\\xxoo.mp4";
        File f1 = new File(filePath);

        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(f1));

        byte[] b1 = new byte[256*1024];
        int x1 = bufferedInputStream.read(b1);

        List<String> strs = new ArrayList<>();

        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(b1, 0, x1);
        String s2 = Hex.encodeHexString(md.digest());

        // 重置摘要信息
        md.reset(); md.update(b1, 0, x1);
        int x2;
        while ((x2 = bufferedInputStream.read(b1)) != -1) {
            md.update(b1, 0, x2);
        }

        bufferedInputStream.close();

        String s1 = Hex.encodeHexString(md.digest());strs.add(s1);
        strs.add(s2);
        String s3 = String.valueOf(f1.length());strs.add(s3);
        String s4 = f1.getName();strs.add(s4);

        String hash = String.join("#", strs);
        System.out.println(hash);
    }

}
