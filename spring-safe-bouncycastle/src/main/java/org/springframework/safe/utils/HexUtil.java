package org.springframework.safe.utils;

/**
 * 进制转换工具类
 * @author Sir.D
 */
public class HexUtil {

    public static String byte2HexString(byte[] var0) {
        StringBuffer var1 = new StringBuffer();

        for(int var2 = 0; var2 < var0.length; ++var2) {
            // & 负数取反+1 -> 补码
            // -21 byte 举例
            // 0001 0101 & 1111 1111
            // 第一步 取反 0001 0101 -> 1110 1010
            // 第二步 +1  0001 0101 -> 1110 1010 -> 十进制为235
            String var3 = Integer.toHexString(var0[var2] & 0xff);
            if (var3.length() < 2) {
                var1.append(0);
            }

            var1.append(var3);
        }

        return var1.toString();
    }

}
