package com.swust.emporium.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import java.security.Key;
import java.security.SecureRandom;

/**
 * @author 雪瞳
 * @Slogan 忘时，忘物，忘我。
 * @Function
 * DES算法是一种对称的加密和解密算法
 */
public class DESUtil {

    private static Key key;

    private static String KEY_STR = "mykey";
    private static String CHARSETNAME = "UTF-8";
    private static String ALGORITHM = "DES";

    static {
        try {
            // 生成DES算法对象
            KeyGenerator generator = KeyGenerator.getInstance(ALGORITHM);
            // 运用SHA1安全策略
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            // 设置秘钥种子
            secureRandom.setSeed(KEY_STR.getBytes());
            // 初始化基于SHA1的算法对象                                                                                                                                                                                                                                                                                                 
            generator.init(secureRandom);
            key = generator.generateKey();
            generator = null;

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String getEncryptString(String str){
        // 基于BASE64编码，接收byte[]并转换成String
        BASE64Encoder base64Encoder = new BASE64Encoder();
        try {
            // 基于UTF-8格式进行编码
            byte[] bytes = str.getBytes(CHARSETNAME);
            // 获取加密对象
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            // 初始化密码信息
            cipher.init(Cipher.ENCRYPT_MODE, key);
            // 加密数据
            byte[] doFinal = cipher.doFinal(bytes);
            // 返回数据
            return base64Encoder.encode(doFinal);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public static String getDecryptString(String str) {
        // 基于BASE64编码接收byte[]并转换成String
        BASE64Decoder base64decoder = new BASE64Decoder();
        try {
            // 将字符串转换成byte[]
            byte[] bytes = base64decoder.decodeBuffer(str);
            // 获取解密对象
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            // 初始化解密信息
            cipher.init(Cipher.DECRYPT_MODE, key);
            // 解密
            byte[] doFinal = cipher.doFinal(bytes);
            // 返回解密信息
            return new String(doFinal, CHARSETNAME);
        } catch (Exception e) {
            // TODO: handle exception
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        System.out.println(getEncryptString("xuetong"));
        System.out.println(getEncryptString("Xiaonie1996!"));
        System.out.println(getDecryptString("o42Cu18ywuM="));
        System.out.println(getDecryptString("9dW2L/H6aXyRp9gO2mfklw=="));
    }
}
