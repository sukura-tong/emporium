package com.swust.emporium.util.wechat.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * @author 雪瞳
 * @Slogan 忘时，忘物，忘我。
 * @Function
 * 微信请求校验接口类
 */
public class SignUtil {
    // 与接口配置的Token字段要一致
    private static String token = "emporium";

    /**
     * 验证签名
     * @param sianature
     * @param timestamp
     * @param nonce
     * @return
     */
    public static boolean checkSignature(String sianature, String timestamp, String nonce){
        String[] arr = new String[]{
                token,timestamp,nonce
        };
        // 字典排序
        Arrays.sort(arr);

        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < arr.length; i++){
            buffer.append(arr[i]);
        }

        MessageDigest messageDigest = null;
        String tmpString = null;

        try {
            messageDigest = MessageDigest.getInstance("SHA-1");
            // 将三个字符串拼接并使用sha-1进行数据加密
            byte[] digest = messageDigest.digest(buffer.toString().getBytes());
            tmpString = byteToString(digest);
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        buffer = null;

        // 将sha1加密后的字符串与signature对比标识该请求来源微信
        return tmpString != null ? tmpString.equals(sianature.toUpperCase()) : false;

    }

    /***
     * 将字节组数据转换为16进制字符串
     * @param digest
     * @return
     */
    private static String byteToString(byte[] digest) {
        String strDigest = "";
        for (int i = 0; i < digest.length; i++){
            strDigest += byteToHexString(digest[i]);
        }
        return strDigest;
    }

    /**
     * 将字节转换为16进制字符串
     * @param b
     * @return
     */
    private static String byteToHexString(byte b) {
        char[] digit = {
                '0','1','2','3','4','5','6','7','8','9'
                ,'A','B','C','D','E','F'
        };

        char[] tempAddr = new char[2];
        tempAddr[0] = digit[(b >>> 4) & 0X0F];
        tempAddr[1] = digit[b & 0X0F];

        String s = new String(tempAddr);
        return s;
    }
}
