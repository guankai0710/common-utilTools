package com.guankai.utiltools;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

/**
 * DES加密算法工具类
 *
 * @author: guan.kai
 * @date: 2020/6/15 10:01
 **/
public class EncoderOfDesUtil {

    private EncoderOfDesUtil() {
    }

    /** 加密工具 */
    private static Cipher desEncryptCipher = null;
    /** 解密工具*/
    private static Cipher desDecryptCipher = null;

    /**
     * 从指定字符串生成密钥，密钥所需的字节数组长度为8位 不足8位时后面补0，超出8位只取前8位
     *
     * @param arrBTmp
     * @return
     */
    public static Key getKey(byte[] arrBTmp) {
        byte[] arrB = new byte[8];
        for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
            arrB[i] = arrBTmp[i];
        }
        Key key = new SecretKeySpec(arrB, "DES");
        return key;
    }

    /**
     * 将byte数组转换为表示16进制值的字符串， 如：byte[]{8,18}转换为：0813， 和public static byte[]
     * hexStr2ByteArr(String strIn) 互为可逆的转换过程
     *
     * @param arrB 需要转换的byte数组
     * @return 转换后的字符串
     * @throws Exception 本方法不处理任何异常，所有异常全部抛出
     */
    public static String byteArr2HexStr(byte[] arrB) throws Exception {
        int iLen = arrB.length;
        // 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
        StringBuffer sb = new StringBuffer(iLen * 2);
        for (int i = 0; i < iLen; i++) {
            int intTmp = arrB[i];
            // 把负数转换为正数
            while (intTmp < 0) {
                intTmp = intTmp + 256;
            }
            // 小于0F的数需要在前面补0
            if (intTmp < 16) {
                sb.append("0");
            }
            sb.append(Integer.toString(intTmp, 16));
        }
        return sb.toString();
    }

    /**
     * 将表示16进制值的字符串转换为byte数组， 和public static String byteArr2HexStr(byte[] arrB)
     * 互为可逆的转换过程
     *
     * @param strIn 需要转换的字符串
     * @return 转换后的byte数组
     */
    public static byte[] hexStr2ByteArr(String strIn) {
        byte[] arrB = strIn.getBytes();
        int iLen = arrB.length;
        // 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
        byte[] arrOut = new byte[iLen / 2];
        for (int i = 0; i < iLen; i = i + 2) {
            String strTmp = new String(arrB, i, 2);
            arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
        }
        return arrOut;
    }

    /**
     * DES加密
     *
     * @param strIn   需要加密字符串
     * @param keyByte 密钥
     * @return
     * @throws Exception
     */
    public static String encryptByDes(String strIn, byte[] keyByte) throws Exception {
        if (desEncryptCipher == null) {
            Key key = getKey(keyByte);
            desEncryptCipher = Cipher.getInstance("DES");
            desEncryptCipher.init(Cipher.ENCRYPT_MODE, key);
        }
        return byteArr2HexStr(desEncryptCipher.doFinal(strIn.getBytes()));
    }

    /**
     * DES解密
     *
     * @param strIn   需解密的字符串
     * @param keyByte 密钥
     * @return
     */
    public static String decryptByDes(String strIn, byte[] keyByte) {
        try {

            if (desDecryptCipher == null) {
                Key key = null;
                key = getKey(keyByte);
                desDecryptCipher = Cipher.getInstance("DES");
                desDecryptCipher.init(Cipher.DECRYPT_MODE, key);
            }
            return new String(desDecryptCipher.doFinal(hexStr2ByteArr(strIn)));
        } catch (Exception e) {
            System.out.println(e.toString() + ";  EncoderOfAesUtil-->decryptByDes" + "解密失败！");
            return null;
        }
    }
}
