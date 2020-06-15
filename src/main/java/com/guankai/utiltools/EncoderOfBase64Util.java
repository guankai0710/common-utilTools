package com.guankai.utiltools;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;

/**
 * Base64加密算法工具类
 *
 * @author: guan.kai
 * @date: 2020/6/15 10:00
 **/
public class EncoderOfBase64Util {

    private EncoderOfBase64Util() {
    }

    /** 加密工具 */
    private static final BASE64Encoder ENCODER = new BASE64Encoder();
    /** 解密工具 */
    private static final BASE64Decoder DECODER = new BASE64Decoder();

    /**
     * Base64加密
     *
     * @param toEncodeContent
     * @return
     */
    public static String encryptByBase64(String toEncodeContent) {
        if (toEncodeContent == null || "".equals(toEncodeContent)) {
            return null;
        }
        return ENCODER.encode(toEncodeContent.getBytes());
    }

    /**
     * Base64解密
     *
     * @param toDecodeContent
     * @return
     */
    public static String decryptByBase64(String toDecodeContent) {
        if (toDecodeContent == null || "".equals(toDecodeContent)) {
            return null;
        }
        byte[] buf = null;
        try {
            buf = DECODER.decodeBuffer(toDecodeContent);
        } catch (IOException e) {
            System.out.println(e.toString() + ";  EncoderOfAesUtil-->decryptByBase64 Base64解密失败！");
        }
        return new String(buf);
    }
}
