package com.wjj.application.facade.ca.casdk.util;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 加密工具
 * @author hank
 */
public class CryptUtils {

    /**
     * 默认的算法
     */
    private static final String DE_KEY_MAC = "HmacMD5";

    /**
     * 默认的字符集
     */
    private static final Charset DE_CHARSET = StandardCharsets.UTF_8;


    /**
     * 使用默认的算法(HmacMD5) 得到hmac 16进制字符串
     * @param inputStr 需要加密的串
     * @param key key
     * @return 16进制字符串
     * @throws Exception
     */
    public static String encryptHMAC(String inputStr, String key) throws Exception {
        return encryptHMAC(inputStr, key, DE_KEY_MAC);
    }
    /**
     * 使用指定的算法得到hmac 16进制字符串
     * @param inputStr 需要加密的串
     * @param key key
     * @param keyMac hmac算法
     * @return 16进制字符串
     * @throws Exception
     */
    public static String encryptHMAC(String inputStr, String key, String keyMac) throws Exception {
        return Hex.encodeHexString(encryptHMAC(inputStr.getBytes(DE_CHARSET), key, keyMac));
    }

    public static String MD5Encode(String origin) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        return Hex.encodeHexString(md.digest(origin.getBytes(DE_CHARSET)));
    }

    private static byte[] encryptHMAC(byte[] data, String key, String keyMac) throws Exception {

        SecretKey secretKey = new SecretKeySpec(key.getBytes(DE_CHARSET), keyMac);
        Mac mac = Mac.getInstance(secretKey.getAlgorithm());
        mac.init(secretKey);
        return mac.doFinal(data);
    }
 
//    public static void main(String[] args) throws Exception{
//        System.out.println("HMACStr:\n" + encryptHMAC("a", "hank"));
//    }
}