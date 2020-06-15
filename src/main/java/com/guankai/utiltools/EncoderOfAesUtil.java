package com.guankai.utiltools;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES加密算法工具类
 *
 * @author: guan.kai
 * @date: 2020/6/10 16:47
 **/
public class EncoderOfAesUtil {

    private EncoderOfAesUtil() {}

    /**
     * AES加密
     *
     * @param sSrc 需要加密的字符串
     * @param sKey 密钥
     * @param vi 使用CBC模式所需向量
     * @return
     * @throws Exception
     */
    public static String encryptByAes(String sSrc, String sKey, String vi) throws Exception {
        if (sKey == null) {
            System.out.print("Key为空null");
            return null;
        }
        // 判断Key是否为16位
        if (sKey.length() != 16) {
            System.out.print("Key长度不是16位");
            return null;
        }
        byte[] raw = sKey.getBytes();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        //"算法/模式/补码方式"
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        //使用CBC模式，需要一个向量iv，可增加加密算法的强度
        IvParameterSpec iv = new IvParameterSpec(vi.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes());
        //此处使用BASE64做转码功能，同时能起到2次加密的作用。
        return new BASE64Encoder().encode(encrypted);
    }

    /**
     * AES解密
     *
     * @param sSrc 需要解密的字符串
     * @param sKey 密钥
     * @param vi 使用CBC模式所需向量
     * @return
     * @throws Exception
     */
    public static String decryptByAes(String sSrc, String sKey, String vi) {
        try {
            // 判断Key是否正确
            if (sKey == null) {
                System.out.print("Key为空null");
                return null;
            }
            // 判断Key是否为16位
            if (sKey.length() != 16) {
                System.out.print("Key长度不是16位");
                return null;
            }
            byte[] raw = sKey.getBytes("ASCII");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(vi.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            //先用base64解密
            byte[] encrypted1 = new BASE64Decoder().decodeBuffer(sSrc);
            try {
                byte[] original = cipher.doFinal(encrypted1);
                String originalString = new String(original);
                return originalString;
            } catch (Exception e) {
                System.out.println(e.toString() + ";  EncoderOfAesUtil-->decryptByAes AES解密失败！");
                return null;
            }
        } catch (Exception ex) {
            System.out.println(ex.toString() + ";  EncoderOfAesUtil-->decryptByAes AES解密失败！");
            return null;
        }
    }
}
