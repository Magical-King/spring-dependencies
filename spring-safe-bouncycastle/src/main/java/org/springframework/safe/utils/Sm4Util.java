package org.springframework.safe.utils;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;

import java.nio.charset.StandardCharsets;

/**
 * @author Sir.D
 */
@Slf4j
@NoArgsConstructor
public class Sm4Util extends org.springframework.safe.utils.cipher.Sm4Util {
    public static String ALGORITHM_KEY;
    private static final String DEFAULT_IV = "FmMaHEhImdE3p8IJ";
    private static final String DEFAULT_PRI = "1ECD02A99F49D47C5874AAA9F1EB37F5";

    static {
        ALGORITHM_KEY = generateKey();
        log.info("=====> SM4秘钥(KEY) : {}", ALGORITHM_KEY );
    }

    public static String encryptByEcb(String var0) {
        return encryptByEcb(var0, false);
    }

    public static String encryptByEcb(String var0, boolean var1) {
        if (var1) {
            return encryptByEcb(ALGORITHM_KEY, var0);
        } else {
            return encryptByEcb(DEFAULT_PRI, var0);
        }
    }

    public static String encryptByEcb(String var0, String var1) {
        String var2 = "";

        try {
            var2 = ByteUtils.toHexString(encryptByEcb(ByteUtils.fromHexString(var0), var1.getBytes(StandardCharsets.UTF_8.name()))).toUpperCase();
        } catch (Exception var) {
            var.printStackTrace();
        }

        return var2;
    }


    public static String decryptByEcb(String var0) {
        return decryptByEcb(var0, false);
    }

    public static String decryptByEcb(String var0, boolean var1) {
        if (var1) {
            return decryptByEcb(ALGORITHM_KEY, var0);
        } else {
            return decryptByEcb(DEFAULT_PRI, var0);
        }
    }

    public static String decryptByEcb(String var0, String var1) {
        String var2 = "";

        try {
            var2 = new String(decryptByEcb(ByteUtils.fromHexString(var0), ByteUtils.fromHexString(var1)), StandardCharsets.UTF_8.name());
        } catch (Exception var) {
            var.printStackTrace();
        }

        return var2;
    }

    public static String encryptByCbc(String var0) {
        return encryptByCbc(var0, false);
    }

    public static String encryptByCbc(String var0, boolean var1) {
        if (var1) {
            return encryptByCbc(ALGORITHM_KEY, DEFAULT_IV, var0);
        } else {
            return encryptByCbc(DEFAULT_PRI, DEFAULT_IV, var0);
        }
    }

    public static String encryptByCbc(String var0, String var1) {
        return encryptByCbc(var0, DEFAULT_IV, var1);
    }

    public static String encryptByCbc(String var0, String var1, String var2) {
        String var3 = "";

        try {
            final byte[] bytes = ByteUtils.fromHexString(var1);
            var3 = ByteUtils.toHexString(encryptByCbc(ByteUtils.fromHexString(var0), var1.getBytes(StandardCharsets.UTF_8.name()), var2.getBytes(StandardCharsets.UTF_8.name()))).toUpperCase();
        } catch (Exception var) {
            var.printStackTrace();
        }

        return var3;
    }

    public static String decryptByCbc(String var0) {
        return decryptByCbc(var0, false);
    }

    public static String decryptByCbc(String var0, boolean var1) {
        if (var1) {
            return decryptByCbc(ALGORITHM_KEY, DEFAULT_IV, var0);
        } else {
            return decryptByCbc(DEFAULT_PRI, var0);
        }
    }

    public static String decryptByCbc(String var0, String var1) {
        return decryptByCbc(var0, DEFAULT_IV, var1);
    }

    public static String decryptByCbc(String var0, String var1, String var2) {
        String var3 = "";

        try {
            var3 = new String(decryptByCbc(ByteUtils.fromHexString(var0), var1.getBytes(StandardCharsets.UTF_8.name()), ByteUtils.fromHexString(var2)), StandardCharsets.UTF_8.name());
        } catch (Exception var) {
            var.printStackTrace();
        }

        return var3;
    }

}
