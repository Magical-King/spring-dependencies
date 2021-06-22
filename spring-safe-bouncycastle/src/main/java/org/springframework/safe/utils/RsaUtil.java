package org.springframework.safe.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.io.IOException;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author Sir.D
 */
@Slf4j
public class RsaUtil {
    private static final String PRIVATE_KEY;
    public static final String PUBLIC_KEY;
    public static final String KEY_ALGORITHM = "RSA";

    static {
        KeyPairGenerator var0 = null;
        try {
            var0 = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException var4) {
            log.error("=====> 创建密钥失败 : {}", var4);
        }

        var0.initialize(1024);
        KeyPair var1 = var0.generateKeyPair();
        PublicKey var2 = var1.getPublic();
        PrivateKey var3 = var1.getPrivate();
        PRIVATE_KEY = encryptBase64(var3.getEncoded());
        PUBLIC_KEY = encryptBase64(var2.getEncoded());
    }

    public static String decrypt(String var0) {
        String var1 = "";

        try {
            var1 = new String(decrypt(decryptBase64(var0)));
        } catch (Exception var) {
            log.error("=====> 解密失败 : {}", var.getMessage());
        }
        return var1;
    }

    public static byte[] decrypt(byte[] var0) {
        byte[] var1 = null;

        try {
            byte[] var2 = decryptBase64(PRIVATE_KEY);
            PKCS8EncodedKeySpec var3 = new PKCS8EncodedKeySpec(var2);
            KeyFactory var4 = KeyFactory.getInstance("RSA");
            PrivateKey var5 = var4.generatePrivate(var3);
            Cipher var6 = Cipher.getInstance(var4.getAlgorithm());
            var6.init(2, var5);
            var1 = var6.doFinal(var0);
        } catch (Exception var) {
            log.error("=====> 解密失败 : {}", var.getMessage());
        }

        return var1;
    }

    public static String encrypt(String var0) {
        String var1 = "";

        try {
            var1 = encryptBase64(encrypt(var0.getBytes()));
        } catch (Exception var) {
            log.error("=====> 加密失败 : {}", var.getMessage());
        }

        return var1;
    }

    public static byte[] encrypt(byte[] var0) {
        byte[] var1 = null;

        try {
            byte[] var2 = decryptBase64(PUBLIC_KEY);
            X509EncodedKeySpec var3 = new X509EncodedKeySpec(var2);
            KeyFactory var4 = KeyFactory.getInstance("RSA");
            PublicKey var5 = var4.generatePublic(var3);
            Cipher var6 = Cipher.getInstance(var4.getAlgorithm());
            var6.init(1, var5);
            var1 = var6.doFinal(var0);
        } catch (Exception var) {
            log.error("=====> 加密失败 : {}", var.getMessage());
        }

        return var1;
    }

    public static byte[] decryptBase64(String var0) throws IOException {
        return (new Base64()).decode(var0);
    }

    public static String encryptBase64(byte[] var0) {
        return (new Base64()).encodeAsString(var0);
    }
}
