package org.springframework.safe.utils;

import lombok.NoArgsConstructor;
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;

import java.security.MessageDigest;

/**
 * @author Sir.D
 */
@NoArgsConstructor
public class Md5Util {
    private static char[] hexdigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String md5Encode(String var0) throws Exception {
        byte[] var1 = var0.getBytes("utf-8");
        MessageDigest var2 = MessageDigest.getInstance("MD5");
        var2.update(var1);
        byte[] var3 = var2.digest();
        return ByteUtils.toHexString(var3);
    }
}
