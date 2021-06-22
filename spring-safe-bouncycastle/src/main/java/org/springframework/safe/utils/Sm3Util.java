package org.springframework.safe.utils;

import lombok.NoArgsConstructor;
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;
import org.springframework.safe.utils.cipher.SmBaseUtil;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Arrays;

/**
 * @author Sir.D
 */
@NoArgsConstructor
public class Sm3Util extends SmBaseUtil {

    public static byte[] digest(byte[] var0) {
        MessageDigest var1 = null;

        try {
            var1 = MessageDigest.getInstance("SM3", "BC");
        } catch (NoSuchProviderException | NoSuchAlgorithmException var3) {
            var3.printStackTrace();
        }

        return var1.digest(var0);
    }

    public static String digest(String var0) {
        String var1 = "";

        try {
            byte[] var2 = digest(var0.getBytes(StandardCharsets.UTF_8));
            var1 = ByteUtils.toHexString(var2).toUpperCase();
        } catch (Exception var3) {
            var3.printStackTrace();
        }

        return var1;
    }

    public static boolean verify(String var0, String var1) {
        boolean var3 = false;

        try {
            byte[] var4 = var0.getBytes(StandardCharsets.UTF_8);
            byte[] var5 = ByteUtils.fromHexString(var1);
            byte[] var6 = digest(var4);

            if (Arrays.equals(var5, var6)) {
                var3 = true;
            }
        } catch (Exception var) {
            var.printStackTrace();
        }

        return var3;
    }



    public static void main(String[] var0) throws Exception {
        String var3 = "12345678";
        System.out.println(Arrays.toString(var3.getBytes()));
        System.out.println(digest(var3));


    }

}
