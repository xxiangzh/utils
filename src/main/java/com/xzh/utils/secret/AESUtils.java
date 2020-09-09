package com.xzh.utils.secret;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES加密工具
 *
 * @author: 向振华
 * @date: 2020/09/08 09:31
 */
@Slf4j
public class AESUtils {

    private static final String AES = "AES";
    private static final String KEY = "3635192653589793";

    /**
     * 加密
     *
     * @param str
     * @return
     */
    public static String encrypt(String str) {
        return encrypt(str, KEY);
    }

    /**
     * 解密
     *
     * @param str
     * @return
     */
    public static String decrypt(String str) {
        return decrypt(str, KEY);
    }

    /**
     * 加密
     *
     * @param src
     * @param key
     * @return
     */
    public static String encrypt(String src, String key) {
        try {
            return byte2hex(encrypt(src.getBytes(), key));
        } catch (Exception e) {
            log.error("加密异常：", e);
        }
        return null;
    }

    /**
     * 解密
     *
     * @param src
     * @param key
     * @return
     */
    public static String decrypt(String src, String key) {
        try {
            return new String(decrypt(hex2byte(src.getBytes()), key));
        } catch (Exception e) {
            log.error("解密异常：", e);
        }
        return null;
    }

    /**
     * byte数组转十六进制字符串
     *
     * @param b
     * @return
     */
    private static String byte2hex(byte[] b) {
        String stmp;
        if (null == b) {
            return "";
        }
        StringBuilder buffer = new StringBuilder();
        for (byte value : b) {
            stmp = (Integer.toHexString(value & 0XFF));
            if (stmp.length() == 1) {
                buffer.append("0").append(stmp);
            } else {
                buffer.append(stmp);
            }
        }
        return buffer.toString().toUpperCase();
    }

    /**
     * 十六进制字符串转byte数组
     *
     * @param b
     * @return
     */
    private static byte[] hex2byte(byte[] b) {
        if (null == b || (b.length % 2) != 0) {
            throw new IllegalArgumentException("长度不是偶数");
        }
        byte[] b2 = new byte[b.length / 2];
        for (int n = 0; n < b.length; n += 2) {
            String item = new String(b, n, 2);
            b2[n / 2] = (byte) Integer.parseInt(item, 16);
        }
        return b2;
    }

    /**
     * 加密
     *
     * @param src
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encrypt(byte[] src, String key) throws Exception {
        Cipher cipher = Cipher.getInstance(AES);
        SecretKeySpec securely = new SecretKeySpec(key.getBytes(), AES);
        // 设置密钥和加密形式
        cipher.init(Cipher.ENCRYPT_MODE, securely);
        return cipher.doFinal(src);
    }

    /**
     * 解密
     *
     * @param src
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decrypt(byte[] src, String key) throws Exception {
        Cipher cipher = Cipher.getInstance(AES);
        // 设置加密Key
        SecretKeySpec securekey = new SecretKeySpec(key.getBytes(), AES);
        // 设置密钥和解密形式
        cipher.init(Cipher.DECRYPT_MODE, securekey);
        return cipher.doFinal(src);
    }
}
