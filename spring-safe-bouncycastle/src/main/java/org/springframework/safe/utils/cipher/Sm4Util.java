package org.springframework.safe.utils.cipher;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.util.encoders.Hex;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

/**
 * @author Sir.D
 */
@Slf4j
@NoArgsConstructor
public class Sm4Util extends SmBaseUtil {
    public static final String ALGORITHM_NAME = "SM4";
    public static final String ALGORITHM_BC = "BC";
    public static final String ALGORITHM_NAME_ECB_PADDING = "SM4/ECB/PKCS5Padding";
    public static final String ALGORITHM_NAME_CBC_PADDING = "SM4/CBC/PKCS5Padding";
    public static final int DEFAULT_KEY_SIZE = 128;

    protected static String generateKey() {
        return (new String(Hex.encode(generateKey(DEFAULT_KEY_SIZE)))).toUpperCase();
    }

    protected static byte[] generateKey(int var0) {
        try {
            KeyGenerator var1 = KeyGenerator.getInstance(ALGORITHM_NAME, ALGORITHM_BC);
            var1.init(var0, new SecureRandom());
            return var1.generateKey().getEncoded();
        } catch (Exception var) {
            var.printStackTrace();
            return null;
        }
    }

    protected static byte[] encryptByEcb(byte[] var0, byte[] var1)  {
        try {
            Cipher var2 = ebc(ALGORITHM_NAME_ECB_PADDING, 1, var0);
            return var2.doFinal(var1);
        } catch (Exception var) {
            var.printStackTrace();
            return null;
        }
    }

    protected static byte[] decryptByEcb(byte[] var0, byte[] var1)  {
        try {
            Cipher var2 = ebc(ALGORITHM_NAME_ECB_PADDING, 2, var0);
            return var2.doFinal(var1);
        } catch (Exception var) {
            var.printStackTrace();
            return null;
        }
    }

    protected static byte[] encryptByCbc(byte[] var0, byte[] var1, byte[] var2)  {
        try {
            Cipher var3 = cbc(ALGORITHM_NAME_CBC_PADDING, 1, var0, var1);
            return var3.doFinal(var2);
        } catch (Exception var) {
            var.printStackTrace();
            return null;
        }
    }

    protected static byte[] decryptByCbc(byte[] var0, byte[] var1, byte[] var2)  {
        try {
            Cipher var3 = cbc(ALGORITHM_NAME_CBC_PADDING, 2, var0, var1);
            return var3.doFinal(var2);
        } catch (Exception var) {
            var.printStackTrace();
            return null;
        }
    }

    private static Cipher ebc(String var0, int var1, byte[] var2) {
        try {
            Cipher var3 = Cipher.getInstance(var0, ALGORITHM_BC);
            SecretKeySpec var4 = new SecretKeySpec(var2, ALGORITHM_NAME);
            var3.init(var1, var4);
            return var3;
        } catch (Exception var) {
            var.printStackTrace();
            return null;
        }
    }

    private static Cipher cbc(String var0, int var1, byte[] var2, byte[] var3) {
        try {
            Cipher var4 = Cipher.getInstance(var0, ALGORITHM_BC);
            SecretKeySpec var5 = new SecretKeySpec(var2, ALGORITHM_NAME);
            IvParameterSpec var6 = new IvParameterSpec(var3);
            var4.init(var1, var5, var6);
            return var4;
        } catch (Exception var) {
            var.printStackTrace();
            return null;
        }
    }

}
