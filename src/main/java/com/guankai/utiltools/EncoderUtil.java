package com.guankai.utiltools;

import org.apache.commons.codec.binary.Hex;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.math.BigInteger;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.HashMap;

/**
 * 常用加密算法工具类
 *
 * @author: guan.kai
 * @date: 2020/6/10 16:47
 **/
public class EncoderUtil {

    private EncoderUtil() {}

    // ************************** Base64--Begin *************************** //

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
            System.out.println(e.toString() + ";  EncoderUtil-->decryptByBase64 Base64解密失败！");
        }
        return new String(buf);
    }
    // ************************** Base64--End *************************** //




    // ************************** DES--Begin *************************** //

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
            System.out.println(e.toString() + ";  EncoderUtil-->decryptByDes" + "解密失败！");
            return null;
        }
    }
    // ************************** DES--Begin *************************** //



    // ************************** RSA BEGIN *************************** //

    /**
     * 生成公钥和私钥
     *
     * @throws NoSuchAlgorithmException
     */
    public static HashMap<String, Object> getRSAKeys() throws NoSuchAlgorithmException {
        HashMap<String, Object> map = new HashMap<>(2);
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(512);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        map.put("public", publicKey);
        map.put("private", privateKey);
        return map;
    }

    /**
     * 使用模和指数生成RSA公钥
     * 注意：【此代码用了默认补位方式，为RSA/None/PKCS1Padding，不同JDK默认的补位方式可能不同，如Android默认是RSA/None/NoPadding】
     *
     * @param modulus  模
     * @param exponent 指数
     * @return
     */
    public static RSAPublicKey getRSAPublicKey(String modulus, String exponent) {
        try {
            BigInteger b1 = new BigInteger(modulus);
            BigInteger b2 = new BigInteger(exponent);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAPublicKeySpec keySpec = new RSAPublicKeySpec(b1, b2);
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            System.out.println(e.toString() + ";  EncoderUtil-->RSAPublicKey 生成RSA公钥失败！");
            return null;
        }
    }

    /**
     * 使用模和指数生成RSA私钥
     * 注意：【此代码用了默认补位方式，为RSA/None/PKCS1Padding，不同JDK默认的补位方式可能不同，如Android默认是RSA/None/NoPadding】
     *
     * @param modulus  模
     * @param exponent 指数
     * @return
     * @throws Exception
     */
    public static RSAPrivateKey getRSAPrivateKey(String modulus, String exponent) {
        try {
            BigInteger b1 = new BigInteger(modulus);
            BigInteger b2 = new BigInteger(exponent);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(b1, b2);
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        } catch (Exception e) {
            System.out.println(e.toString() + ";  EncoderUtil-->getRSAPrivateKey 生成RSA私钥失败！");
            return null;
        }
    }

    /**
     * 公钥加密
     *
     * @param data 需要加密的字符串
     * @param publicKey 公钥
     * @return
     * @throws Exception
     */
    public static String encryptByRSAPublicKey(String data, RSAPublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        // 模长
        int keyLen = publicKey.getModulus().bitLength() / 8;
        // 加密数据长度 <= 模长-11
        String[] datas = splitString(data, keyLen - 11);
        String mi = "";
        //如果明文长度大于模长-11则要分组加密
        for (String s : datas) {
            mi += bcd2Str(cipher.doFinal(s.getBytes()));
        }
        return mi;
    }

    /**
     * 私钥解密
     *
     * @param data 需要解密的字符串
     * @param privateKey 私钥
     * @return
     * @throws Exception
     */
    public static String decryptByRSAPrivateKey(String data, RSAPrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        //模长
        int keyLen = privateKey.getModulus().bitLength() / 8;
        byte[] bytes = data.getBytes();
        byte[] bcd = asciiToBcd(bytes, bytes.length);
        //如果密文长度大于模长则要分组解密
        String ming = "";
        byte[][] arrays = splitArray(bcd, keyLen);
        for (byte[] arr : arrays) {
            ming += new String(cipher.doFinal(arr));
        }
        return ming;
    }

    /**
     * ASCII码转BCD码
     */
    public static byte[] asciiToBcd(byte[] ascii, int ascLen) {
        byte[] bcd = new byte[ascLen / 2];
        int j = 0;
        for (int i = 0; i < (ascLen + 1) / 2; i++) {
            bcd[i] = ascToBcd(ascii[j++]);
            bcd[i] = (byte) (((j >= ascLen) ? 0x00 : ascToBcd(ascii[j++]) & 0xff) + (bcd[i] << 4));
        }
        return bcd;
    }

    /**
     * ASC码转BCD码
     */
    public static byte ascToBcd(byte asc) {
        byte bcd;

        if ((asc >= '0') && (asc <= '9')){
            bcd = (byte) (asc - '0');
        } else if ((asc >= 'A') && (asc <= 'F')) {
            bcd = (byte) (asc - 'A' + 10);
        } else if ((asc >= 'a') && (asc <= 'f')) {
            bcd = (byte) (asc - 'a' + 10);
        } else {
            bcd = (byte) (asc - 48);
        }
        return bcd;
    }

    /**
     * BCD转字符串
     */
    public static String bcd2Str(byte[] bytes) {
        char[] temp = new char[bytes.length * 2];
        char val;

        for (int i = 0; i < bytes.length; i++) {
            val = (char) (((bytes[i] & 0xf0) >> 4) & 0x0f);
            temp[i * 2] = (char) (val > 9 ? val + 'A' - 10 : val + '0');

            val = (char) (bytes[i] & 0x0f);
            temp[i * 2 + 1] = (char) (val > 9 ? val + 'A' - 10 : val + '0');
        }
        return new String(temp);
    }

    /**
     * 拆分字符串
     */
    public static String[] splitString(String string, int len) {
        int x = string.length() / len;
        int y = string.length() % len;
        int z = 0;
        if (y != 0) {
            z = 1;
        }
        String[] strings = new String[x + z];
        String str = "";
        for (int i = 0; i < x + z; i++) {
            if (i == x + z - 1 && y != 0) {
                str = string.substring(i * len, i * len + y);
            } else {
                str = string.substring(i * len, i * len + len);
            }
            strings[i] = str;
        }
        return strings;
    }

    /**
     * 拆分数组
     */
    public static byte[][] splitArray(byte[] data, int len) {
        int x = data.length / len;
        int y = data.length % len;
        int z = 0;
        if (y != 0) {
            z = 1;
        }
        byte[][] arrays = new byte[x + z][];
        byte[] arr;
        for (int i = 0; i < x + z; i++) {
            arr = new byte[len];
            if (i == x + z - 1 && y != 0) {
                System.arraycopy(data, i * len, arr, 0, y);
            } else {
                System.arraycopy(data, i * len, arr, 0, len);
            }
            arrays[i] = arr;
        }
        return arrays;
    }

    // ************************** RSA END *************************** //




    // ************************** MD5 BEGIN *************************** //

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
            System.out.println(e.toString() + ";  EncoderUtil-->getMD5 对字符串md5加密失败！");
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
    // ************************** MD5 END *************************** //



    // ************************** AES BEGIN *************************** //

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
                System.out.println(e.toString() + ";  EncoderUtil-->decryptByAes AES解密失败！");
                return null;
            }
        } catch (Exception ex) {
            System.out.println(ex.toString() + ";  EncoderUtil-->decryptByAes AES解密失败！");
            return null;
        }
    }
    // ************************** AES DNE *************************** //

}
