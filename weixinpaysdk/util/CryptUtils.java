package com.wjj.application.util.weixinpaysdk.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 加密工具
 * @author hank
 */
public class CryptUtils {

    /**
     * 默认的
     */
    private static final String DE_KEY_MAC = "HmacMD5";


    /**
     * HmacMD5
     * @param inputStr
     * @param key
     * @return
     * @throws Exception
     */
    public static String encryptHMAC(String inputStr, String key) throws Exception {
        return encryptHMAC(inputStr, key, DE_KEY_MAC);
    }
    /**
     * 得到hmac 16进制 copyBy: https://blog.csdn.net/wangshuang1631/article/details/52598401
     * @param inputStr
     * @param key
     * @return
     * @throws Exception
     */
    public static String encryptHMAC(String inputStr, String key, String keyMac) throws Exception {
        return new BigInteger(encryptHMAC(inputStr.getBytes(), key, keyMac)).toString(16);
    }

    public static String MD5Encode(String origin) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        return new BigInteger(md.digest(origin.getBytes())).toString(16);
    }

    private static byte[] encryptHMAC(byte[] data, String key, String keyMac) throws Exception {
 
        SecretKey secretKey = new SecretKeySpec(decryptBASE64(key), keyMac);
        Mac mac = Mac.getInstance(secretKey.getAlgorithm());
        mac.init(secretKey);
        return mac.doFinal(data);
    }

    private static String encryptBASE64(byte[] key) throws Exception {
        return (new BASE64Encoder()).encodeBuffer(key);
    }

    private static byte[] decryptBASE64(String key) throws Exception {
        return (new BASE64Decoder()).decodeBuffer(key);
    }
 
//    public static void main(String[] args) throws Exception{
//        String inputStr="这是一个测试字符串aaabbbccc111222333";
//        System.out.println("原始数据："+inputStr);
//
////        String key = initMacKey();
//        String key = "厉害了";
//        System.out.println("Mac密钥:\n" + key);
//
//        System.out.println("HMACStr:\n" + encryptHMAC(inputStr, key));
//    }
}