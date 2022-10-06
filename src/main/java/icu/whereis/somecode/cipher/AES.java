package icu.whereis.somecode.cipher;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.ArrayUtils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.util.Arrays;

/**
 * Created by tehcpu on 11/12/17.
 * https://github.com/tehcpu/tiny-AES/blob/master/src/ru/tehcpu/tinyaes/core/AES.java
 */
public class AES {
    /**
     * The constant that denotes the algorithm being used.
     */
    private static final String algorithm = "AES";

    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";

    // 16个字符
    private static final String initVector = "epwqkaeel2ssgd53";

    private static final int KEY_LENGTH = 256;

    /**
     * 默认的加密密钥key
     */
    private static final String default_key = "my_key";

    /**
     * The private constructor to prevent instantiation of this object.
     */
    private AES() {

    }

    /**
     * The method that will generate a random {@link SecretKey}.
     *
     * @return The key generated.
     */
    public static SecretKey generateKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);
            keyGenerator.init(KEY_LENGTH);
            return keyGenerator.generateKey();
        } catch (Exception ex) {

        }
        return null;
    }

    /**
     * Creates a new {@link SecretKey} based on a password.
     *
     * @param password
     *      The password that will be the {@link SecretKey}.
     *
     * @return The key.
     */
    public static SecretKey createKey(String password) {
        try {
            byte[] key = password.getBytes("UTF-8");
            MessageDigest sha = MessageDigest.getInstance("SHA-1");

            // 只有20字节
            key = sha.digest(key);

            // 叠加一份
            key = ArrayUtils.addAll(key, key);

            // 根据key的长位取字节
            key = Arrays.copyOf(key, KEY_LENGTH/8); // use only first 128 bit

            return new SecretKeySpec(key, algorithm);
        } catch (Exception ex) {

        }

        return null;
    }

    /**
     * Creates a new {@link SecretKey} based on a password with a specified salt.
     *
     * @param salt
     *      The random salt.
     *
     * @param password
     *      The password that will be the {@link SecretKey}.
     *
     * @return The key.
     */
    public static SecretKey createKey(byte[] salt, String password) {
        try {
            byte[] key = (salt + password).getBytes("UTF-8");
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = ArrayUtils.addAll(key, key);
            key = Arrays.copyOf(key, KEY_LENGTH/8); // use only first 128 bit

            return new SecretKeySpec(key, algorithm);
        } catch (Exception ex) {

        }

        return null;
    }

    /**
     * The method that writes the {@link SecretKey} to a file.
     *
     * @param key
     *      The key to write.
     *
     * @param file
     *      The file to create.
     *
     * @throws IOException
     *      If the file could not be created.
     */
    public static void writeKey(SecretKey key, File file) throws IOException {
        try (FileOutputStream fis = new FileOutputStream(file)) {
            fis.write(key.getEncoded());
        }
    }

    /**
     * Gets a {@link SecretKey} from a {@link File}.
     *
     * @param file
     *      The file that is encoded as a key.
     *
     * @throws IOException
     *      The exception thrown if the file could not be read as a {@link SecretKey}.
     *
     * @return The key.
     */
    public static SecretKey getSecretKey(File file) throws IOException {
        return new SecretKeySpec(Files.readAllBytes(file.toPath()), algorithm);
    }

    public static byte[] encrypt(byte[] message) {
        SecretKey secretKey = createKey(default_key);
        return encrypt(secretKey, message);
    }

    /**
     * The method that will encrypt data.
     *
     * @param secretKey
     *      The key used to encrypt the data.
     *
     * @param data
     *      The data to encrypt.
     *
     * @return The encrypted data.
     */
    public static byte[] encrypt(SecretKey secretKey, byte[] data) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));

            // AES默认是AES/ECB/PKCS5Padding  https://blog.csdn.net/weixin_42503008/article/details/114386585
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
            return cipher.doFinal(data);
        } catch (Exception ex) {

        }

        return null;

    }

    public static byte[] decrypt(byte[] encrypted) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));

            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, createKey(default_key), iv);
            return cipher.doFinal(encrypted);
        } catch (Exception ex) {

        }
        return null;
    }

    /**
     * The method that will decrypt a piece of encrypted data.
     *
     * @param password
     *      The password used to decrypt the data.
     *
     * @param encrypted
     *      The encrypted data.
     *
     * @return The decrypted data.
     */
    public static byte[] decrypt(String password, byte[] encrypted) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));

            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, createKey(password), iv);
            return cipher.doFinal(encrypted);
        } catch (Exception ex) {

        }
        return null;
    }

    /**
     * The method that will decrypt a piece of encrypted data.
     *
     * @param secretKey
     *      The key used to decrypt encrypted data.
     *
     * @param encrypted
     *      The encrypted data.
     *
     * @return The decrypted data.
     */
    public static byte[] decrypt(SecretKey secretKey, byte[] encrypted) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));

            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
            return cipher.doFinal(encrypted);
        } catch (Exception ex) {

        }
        return null;
    }

    /**
     * 2019年6月16日 20:51:27<br/>
     * 快捷操作方式，加密。和下面成对使用
     *
     * @param message
     * @return
     */
    public static String encrypt(String message) {
        return encrypt(default_key, message);
    }

    /**
     * 2019年6月16日 20:54:04<br/>
     * 快捷操作方式，解密。和上面成对使用
     *
     * @param ciphertext
     * @return
     */
    public static String decrypt(String ciphertext) {
        if (ciphertext == null) return null;

        byte[] encrypted = Base64.decodeBase64(ciphertext);
        byte[] decrypted = decrypt(default_key, encrypted);

        return decrypted==null ? null : new String(decrypted);
    }

    /**
     * 2018年1月27日 11:44:31<br>
     * 快捷操作方式，加密。和下面成对使用
     *
     * @param password
     * @param message
     * @return
     */
    public static String encrypt(String password, String message) {
        if (message == null) return null;

        // the bytes you want to encrypt
        byte[] messageb = message.getBytes();

        // create a key by using your own password
        SecretKey secretKey = createKey(password);

        // encrypt the message using the key that was generated
        byte[] encrypted = encrypt(secretKey, messageb);

        return Base64.encodeBase64URLSafeString(encrypted);
    }

    /**
     * 2018年1月27日 11:48:49<br>
     * 快捷操作方式，解密。和上面成对使用
     *
     * @param password
     * @param ciphertext
     * @return
     */
    public static String decrypt(String password, String ciphertext) {
        if (ciphertext == null) return null;

        byte[] encrypted = Base64.decodeBase64(ciphertext);
        byte[] decrypted = decrypt(password, encrypted);

        return decrypted==null ? null : new String(decrypted);
    }
}
