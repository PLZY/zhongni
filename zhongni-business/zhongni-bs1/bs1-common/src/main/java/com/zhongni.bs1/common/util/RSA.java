package com.zhongni.bs1.common.util;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class RSA {

    /**
     * 密钥长度，DSA算法的默认密钥长度是1024
     * 密钥长度必须是64的倍数，在512到65536位之间
     * */
    private static final int KEY_SIZE=1024;

    public String getSign(String body, String myTime){
        String rsaPrivateKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCMgjIiFVuMeIEt\n" +
                "8nnyRZpUb0N6T9g5vBHd1XaVCdbHVWQvy+Jo2OnfW2C1hlLWSJHw/uGTjg3zBcax\n" +
                "ErJnygSZKEeUGcK/3t8bnVTjbHG5Hzy0JfbSKF5e/BnmtvxuPSb1TEtDXw30YqWF\n" +
                "QfbTa3q8e3XMJc3nWLNRvb9Hpq4zQxNDePvOnXANH8oK/iCrwbDxDtFjlcW0Qa+l\n" +
                "reNMR+Dh+6hsOz9z5XRdn2rEo/mQE+DLbV5NjgSD37Xc8wfJfEDyiQ1kf3MXTFot\n" +
                "/0PaghNnpu0B0HtMIcS1t2zXjtdxnDS0fnyj6hrKfoUxi6fnu7yMzcrUhX7wq/x4\n" +
                "wYr57C+JAgMBAAECggEAXPDJ5I2oDtSu7guT6lNr8CngwokKB7aFQu0uEQt/DD9J\n" +
                "k+wTCTNiD6JL6Aa/yIocLwMJhybRx0yszvgFFm4keIKSRj57+ZqcjZGWUxCqcL9e\n" +
                "0fLCvuSA9+dd4qRreSfuGxeqORgpDNwrjGfDsrlHaGFp2+2tszeS9aDDY8u3MqRR\n" +
                "kgeJHfTUUUOpesIAQPPpKWrQFlMswlaUA+QdIrGAXj/vD8F/My6A4xiKtP8AUw63\n" +
                "x75JSE4dInM4ZicRSO1DyoQV4tqosnNfb5ZC/SxTB6md1VRreMtSdqDeA4VKTJtH\n" +
                "iDOcdYkAXtRdLXauX1hgqaJn8STorVCBv7avg7tRWQKBgQDQjNprY5GEp3Dox2m+\n" +
                "TX9Ti0dcqHT+LsQnkEI/w8Nb3UjwkZ9EYxRsdP8aepOCBzrOYVbSLGUr9iCw4mri\n" +
                "eYhv+LlV9ERf47PKjcqtDSnSEn3Nn9HpE5JWLIyY8z7zR5jLLunoX4TzxHlWR1m+\n" +
                "si6Wd2qxy+fRprRbXMJam2j9kwKBgQCsejZToXii/wX+CF/8QgG5MgYvsJvjolYa\n" +
                "2q8z22P8fIp7kr+z79Gb9cBekkQGeBIE1EdqImpB9k4ygFcKIraw4xaLNosboMBX\n" +
                "MyJTe9GNJYMKoCQ+Nw48w+UOqQmeKW/k2op0EGUqMJgQFuxntwd5Pl1KEy59nnqL\n" +
                "1jQ5Y+Sv8wKBgQCLpYWsWyPZ2/UzKjSYhXQ5EQ5b8UFF4zLd+y8hW4i5PtfUnu4y\n" +
                "8NWAOmeH54RWkVjDUVK9sZSdd1Y0m6hOtmqx767K66HNQ7/kZkXM0v+p90/eHKKG\n" +
                "m+Bla7dJw7fnV43soCFoxG1Wfb1yFJz3oICpqJT9MTitnh8u/lH2MhJ/mQKBgQCr\n" +
                "4p+BeMngpoYyQ+ZkXuOYzGGd5UN197IX0RgJdfqPBQ59WCH0EGu0IZU0qk9gowhS\n" +
                "B7uj6eg7FFuZuI0FbNLkNWuiERlQz0Lfo/XxfNO49klBrZZw6i0mIZOEqLENDf6U\n" +
                "87ZHNI+dHBBu+GlgcubfzWbrC3KpfeZitXYWO5cMQQKBgDpGOlb7rx1K0hzwgYU2\n" +
                "lhG3yaGKwTl1Cfk99Fix7UZowZUQKLtL4sNFIcg3ucmeRYPsoOYz/HDVmh4PzB1V\n" +
                "T39lI4Ev81Bxs8Gb/SArNF3zGG9jyKchOfRXylbzrEBmLmWhzsfGYnqCVwfSIlph\n" +
                "CMMCPQKAe+ST+pbnPwuWoG9m";
        return StandardSecurityUtil.getSign(body, myTime, rsaPrivateKey);
    }

    public static void main(String[] args) throws Exception {
        String body = "{\"Transtate\":\"0\",\"TranStateDetail\":\"success\",\"Appserialno\":\"20211117162210TY0283\",\"Oserialno\":\"2\",\"TransactionAccountID\":\"JYZH424165704804\",\"TAAccountID\":\"\"}";

        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");

        String myTime = sdFormat.format(new Date());
        String privateKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCMgjIiFVuMeIEt\n" +
                "8nnyRZpUb0N6T9g5vBHd1XaVCdbHVWQvy+Jo2OnfW2C1hlLWSJHw/uGTjg3zBcax\n" +
                "ErJnygSZKEeUGcK/3t8bnVTjbHG5Hzy0JfbSKF5e/BnmtvxuPSb1TEtDXw30YqWF\n" +
                "QfbTa3q8e3XMJc3nWLNRvb9Hpq4zQxNDePvOnXANH8oK/iCrwbDxDtFjlcW0Qa+l\n" +
                "reNMR+Dh+6hsOz9z5XRdn2rEo/mQE+DLbV5NjgSD37Xc8wfJfEDyiQ1kf3MXTFot\n" +
                "/0PaghNnpu0B0HtMIcS1t2zXjtdxnDS0fnyj6hrKfoUxi6fnu7yMzcrUhX7wq/x4\n" +
                "wYr57C+JAgMBAAECggEAXPDJ5I2oDtSu7guT6lNr8CngwokKB7aFQu0uEQt/DD9J\n" +
                "k+wTCTNiD6JL6Aa/yIocLwMJhybRx0yszvgFFm4keIKSRj57+ZqcjZGWUxCqcL9e\n" +
                "0fLCvuSA9+dd4qRreSfuGxeqORgpDNwrjGfDsrlHaGFp2+2tszeS9aDDY8u3MqRR\n" +
                "kgeJHfTUUUOpesIAQPPpKWrQFlMswlaUA+QdIrGAXj/vD8F/My6A4xiKtP8AUw63\n" +
                "x75JSE4dInM4ZicRSO1DyoQV4tqosnNfb5ZC/SxTB6md1VRreMtSdqDeA4VKTJtH\n" +
                "iDOcdYkAXtRdLXauX1hgqaJn8STorVCBv7avg7tRWQKBgQDQjNprY5GEp3Dox2m+\n" +
                "TX9Ti0dcqHT+LsQnkEI/w8Nb3UjwkZ9EYxRsdP8aepOCBzrOYVbSLGUr9iCw4mri\n" +
                "eYhv+LlV9ERf47PKjcqtDSnSEn3Nn9HpE5JWLIyY8z7zR5jLLunoX4TzxHlWR1m+\n" +
                "si6Wd2qxy+fRprRbXMJam2j9kwKBgQCsejZToXii/wX+CF/8QgG5MgYvsJvjolYa\n" +
                "2q8z22P8fIp7kr+z79Gb9cBekkQGeBIE1EdqImpB9k4ygFcKIraw4xaLNosboMBX\n" +
                "MyJTe9GNJYMKoCQ+Nw48w+UOqQmeKW/k2op0EGUqMJgQFuxntwd5Pl1KEy59nnqL\n" +
                "1jQ5Y+Sv8wKBgQCLpYWsWyPZ2/UzKjSYhXQ5EQ5b8UFF4zLd+y8hW4i5PtfUnu4y\n" +
                "8NWAOmeH54RWkVjDUVK9sZSdd1Y0m6hOtmqx767K66HNQ7/kZkXM0v+p90/eHKKG\n" +
                "m+Bla7dJw7fnV43soCFoxG1Wfb1yFJz3oICpqJT9MTitnh8u/lH2MhJ/mQKBgQCr\n" +
                "4p+BeMngpoYyQ+ZkXuOYzGGd5UN197IX0RgJdfqPBQ59WCH0EGu0IZU0qk9gowhS\n" +
                "B7uj6eg7FFuZuI0FbNLkNWuiERlQz0Lfo/XxfNO49klBrZZw6i0mIZOEqLENDf6U\n" +
                "87ZHNI+dHBBu+GlgcubfzWbrC3KpfeZitXYWO5cMQQKBgDpGOlb7rx1K0hzwgYU2\n" +
                "lhG3yaGKwTl1Cfk99Fix7UZowZUQKLtL4sNFIcg3ucmeRYPsoOYz/HDVmh4PzB1V\n" +
                "T39lI4Ev81Bxs8Gb/SArNF3zGG9jyKchOfRXylbzrEBmLmWhzsfGYnqCVwfSIlph\n" +
                "CMMCPQKAe+ST+pbnPwuWoG9m";
        String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjIIyIhVbjHiBLfJ58kWa\n" +
                "VG9Dek/YObwR3dV2lQnWx1VkL8viaNjp31tgtYZS1kiR8P7hk44N8wXGsRKyZ8oE\n" +
                "mShHlBnCv97fG51U42xxuR88tCX20iheXvwZ5rb8bj0m9UxLQ18N9GKlhUH202t6\n" +
                "vHt1zCXN51izUb2/R6auM0MTQ3j7zp1wDR/KCv4gq8Gw8Q7RY5XFtEGvpa3jTEfg\n" +
                "4fuobDs/c+V0XZ9qxKP5kBPgy21eTY4Eg9+13PMHyXxA8okNZH9zF0xaLf9D2oIT\n" +
                "Z6btAdB7TCHEtbds147XcZw0tH58o+oayn6FMYun57u8jM3K1IV+8Kv8eMGK+ewv\n" +
                "iQIDAQAB";
//        String publicKey ="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDkBqmnL3DryPhPila3AjvRPoDEkR8WjhzL+ZGqiZV6Ty/CmMVEFLnmtglUxB97wNtq281dZrFxT9lWg1aC36IFwNRz5qPa3xB/XHuJvqjhwoscCY1n+E3A2NVCAVly8ORETlbAsCY9EQatsvqGAyIEs7PQfchsAoaCxIQN1Wt/NQIDAQAB";
//        String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAOQGqacvcOvI+E+KVrcCO9E+gMSRHxaOHMv5kaqJlXpPL8KYxUQUuea2CVTEH3vA22rbzV1msXFP2VaDVoLfogXA1HPmo9rfEH9ce4m+qOHCixwJjWf4TcDY1UIBWXLw5EROVsCwJj0RBq2y+oYDIgSzs9B9yGwChoLEhA3Va381AgMBAAECgYB0GxhjcnqS9F/LUGtbAB4pKyQx8prbxzzOd6M+LJQaFRwCf/th8RO/b81Mhhhp0QdoX8cv2cxcIAZQUMvKE8jRIxAeuZzRTO9lolbukMDvLqr4N4ld4V1TjgIs8N6OQMJIDSBBoTBxMtgWllayT0Ke3MCIq8zw8qkaXCxopYPqQQJBAPR1e6NmX5XemeMWFAN8IFycxlDoN1vjiVKQzXvm1osDh2if1kNoSjcah4/zzRhow1CcRxr3kymHkp1xnnR8epkCQQDuypKd1Ffv8EjqPZta2sMmfGu0Pre3iERbP1LCJiHXpJ4IEL5wtx4zeGuPqTtRg/PVGJGvHsDyNEc+6MmctMb9AkEAyOJOIp16yO5u6PR4p9B1y4UtVskszL7zVr0Vjs7H2N6ihHBso2HtMtEDO2EuVfOpJPH3YIq4DPWSnv0CKFkuaQJBAJfteOVEMgLt7WZjdhGTODCnXttaQAdcgqlPm7b9DfEmwok9Jf2O9H8o8b6AnMYbXyrIrAAwX/B6BI5pC8VRW30CQE2HZ6y/km1H7fUD6KB1mYmzHfYWXuZCWL+LnHXExkuPuylbkl8XA6wF74t/QCjVTR9OEc96b3z5m9WnOMu7h4U=";
//        String content = "1234abcd5678";
//
//        System.out.println("公钥："+publicKey);
//        System.out.println("私钥："+privateKey);
//        System.out.println("明文："+content);
//
//        String zhangyou = encrypt(content, publicKey);
//        System.out.println("加密："+zhangyou);
//
//        String decrypt = decrypt(zhangyou, privateKey);
//        System.out.println("解密："+decrypt);
        long l = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
            String sign = StandardSecurityUtil.getSign(body, myTime, privateKey);
            System.out.println(myTime);
            System.out.println("签名："+sign);
            System.out.println(StandardSecurityUtil.isVerify(body, sign, myTime, publicKey));
        }
        System.out.println("时间：" + (System.currentTimeMillis() - l));
    }

    //公钥加密
    public static String encrypt(String content, String publicKey) throws Exception {
        byte[] decoded = Base64.decodeBase64(publicKey);
        RSAPublicKey puKey = (RSAPublicKey)KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
        Cipher cipher = Cipher.getInstance("RSA");//java默认"RSA"="RSA/ECB/PKCS1Padding"
        cipher.init(Cipher.ENCRYPT_MODE, puKey);
        String outStr = Base64.encodeBase64String(cipher.doFinal(content.getBytes("UTF-8")));
        return outStr;
    }

    public static String decrypt(String content, String privateKey) throws Exception {
        byte[] inputByte = Base64.decodeBase64(content.getBytes(StandardCharsets.UTF_8));
        byte[] decoded = Base64.decodeBase64(privateKey);
        RSAPrivateKey priKey = (RSAPrivateKey)KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE,priKey);
        String s = new String(cipher.doFinal(inputByte));
        return s;
    }
}