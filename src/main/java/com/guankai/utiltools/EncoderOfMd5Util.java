package com.guankai.utiltools;

import org.apache.commons.codec.binary.Hex;

import java.security.MessageDigest;

/**
 * MD5加密算法工具类
 *
 * @author: guan.kai
 * @date: 2020/6/15 10:03
 **/
public class EncoderOfMd5Util {

    private EncoderOfMd5Util() {
    }

    /**
     * 对字符串md5加密
     * 返回32位大写字符串
     *
     * @param str 需要加密的字符串
     * @return
     */
    public static String getMD5(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes("utf-8"));
            byte[] b = md.digest();
            int i;
            StringBuffer buf = new StringBuffer();
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }
            //32位加密
            return buf.toString().toUpperCase();
        } catch (Exception e) {
            System.out.println(e.toString() + ";  EncoderOfAesUtil-->getMD5 对字符串md5加密失败！");
            return null;
        }
    }

    /**
     * 加盐md5加密
     *
     * @param str 需要加密的字符串
     * @param salt 加密盐
     * @return
     */
    public static String getSaltMD5(String str, String salt) {
        // 加密盐
        StringBuilder saltBuilder = new StringBuilder(salt);
        int len = saltBuilder.length();
        if (len < 16) {
            for (int i = 0; i < 16 - len; i++) {
                saltBuilder.append("0");
            }
        }
        salt = saltBuilder.toString();
        str = md5Hex(str + salt);
        char[] cs = new char[48];
        for (int i = 0; i < 48; i += 3) {
            cs[i] = str.charAt(i / 3 * 2);
            char c = salt.charAt(i / 3);
            cs[i + 1] = c;
            cs[i + 2] = str.charAt(i / 3 * 2 + 1);
        }
        return String.valueOf(cs);
    }

    /**
     * 使用Apache的Hex类实现Hex(16进制字符串和)和字节数组的互转
     *
     * @param str
     * @return
     */
    private static String md5Hex(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(str.getBytes());
            return new String(new Hex().encode(digest));
        } catch (Exception e) {
            System.out.println(e.toString());
            return "";
        }
    }
}
