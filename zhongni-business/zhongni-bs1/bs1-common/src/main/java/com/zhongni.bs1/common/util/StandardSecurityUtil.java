package com.zhongni.bs1.common.util;

import org.apache.commons.codec.binary.Base64;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.regex.Pattern;

/**
 *
 */
public class StandardSecurityUtil {

    public static final String KEY_ALGORITHM = "RSA";

    /**
     *
     */
    private static final Pattern pattern = Pattern.compile("\\s*|\t|\r|\n");

    /**
     * 获取签名
     *
     * @param body      String
     * @param pivateKey String
     * @return sign
     */
    private static String getSign(String body, String pivateKey) {
        String sign = null;
        try {
            sign = sign(body, pivateKey);
        } catch (Exception e) {
        }
        return sign;
    }

    /**
     * 获取签名
     *
     * @param body      String
     * @param sendTime  String
     * @param pivateKey String
     * @return sign
     */
    public static String getSign(String body, String sendTime, String pivateKey) {
        return getSign(sendTime + "&" + body, pivateKey);
    }

    /**
     * 验签
     *
     * @param body      String
     * @param sign      String
     * @param sendTime  String
     * @param publicKey String
     * @return isVerify
     */
    public static boolean isVerify(String body, String sign, String sendTime, String publicKey) {
        return isVerify(sendTime + "&" + body, sign, publicKey);
    }

    /**
     * 验签
     *
     * @param body      String
     * @param sign      String
     * @param publicKey String
     * @return boolean
     */
    private static boolean isVerify(String body, String sign, String publicKey) {
        boolean verify = false;
        try {
//            verify = verify(body, sign, DES.decrypt(publicKey));
            verify = verify(body, sign, publicKey);
        } catch (Exception e) {
        }
        return verify;
    }

    private static String sign(String in, String pivateKey) throws Exception {
        Signature signa = Signature.getInstance("SHA512WithRSA");
        //Signature signa = Signature.getInstance("MD5WithRSA");
        // 取私钥匙对象
        PrivateKey priKey = toPrivateKey(pivateKey);
        signa.initSign(priKey);
        signa.update(in.getBytes(StandardCharsets.UTF_8));
        byte[] signdata = signa.sign();
        return Base64.encodeBase64String(signdata);
    }

    public static String signWithGBK(String in, String pivateKey) throws Exception {
        Signature signa = Signature.getInstance("SHA512WithRSA");
        //Signature signa = Signature.getInstance("MD5WithRSA");
        // 取私钥匙对象
        PrivateKey priKey = toPrivateKey(pivateKey);
        signa.initSign(priKey);
        signa.update(in.getBytes("GBK"));
        byte[] signdata = signa.sign();
        return Base64.encodeBase64String(signdata);
    }

    /*
     * SHA1WithRSA验签
     */
    private static boolean verify(String in, String signData, String publicKey) throws Exception {
        boolean flag = false;
        try {
            Signature signa = Signature.getInstance("SHA512WithRSA");
            //Signature signa = Signature.getInstance("MD5WithRSA");
            // 取公钥匙对象
            PublicKey pubKey = toPublicKey(publicKey);
            signa.initVerify(pubKey);
            signa.update(in.getBytes(StandardCharsets.UTF_8));
            byte[] sign_byte = Base64.decodeBase64(signData);
            flag = signa.verify(sign_byte);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception("验签失败，", e);
        }
        return flag;
    }

    public static boolean verifyWithGBK(String in, String signData, String publicKey) throws Exception {
        boolean flag = false;
        try {
            Signature signa = Signature.getInstance("SHA512WithRSA");
            //Signature signa = Signature.getInstance("MD5WithRSA");
            // 取公钥匙对象
            PublicKey pubKey = toPublicKey(publicKey);
            signa.initVerify(pubKey);
            signa.update(in.getBytes("GBK"));
            byte[] sign_byte = Base64.decodeBase64(signData);
            flag = signa.verify(sign_byte);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception("验签失败，", e);
        }
        return flag;
    }


    private static PrivateKey toPrivateKey(String pivateKey) throws Exception {
        byte[] data = Base64.decodeBase64(pivateKey);
        PKCS8EncodedKeySpec pkcs8Enc = new PKCS8EncodedKeySpec(data);
        KeyFactory keyFactory = null;
        PrivateKey priKey = null;
        keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        priKey = keyFactory.generatePrivate(pkcs8Enc);
        return priKey;
    }

    /**
     * 转换公钥
     *
     * @param publicKeyString base64公钥字符�?
     * @return 公钥对象
     */
    private static PublicKey toPublicKey(String publicKeyString) throws Exception {
        byte[] data = Base64.decodeBase64(publicKeyString);
        X509EncodedKeySpec x509Enc = new X509EncodedKeySpec(data);
        KeyFactory keyFactory = null;
        PublicKey publicKey = null;
        keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        publicKey = keyFactory.generatePublic(x509Enc);
        return publicKey;
    }
}