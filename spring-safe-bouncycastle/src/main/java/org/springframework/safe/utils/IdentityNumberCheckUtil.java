package org.springframework.safe.utils;

/**
 * @author Sir.D
 */
public class IdentityNumberCheckUtil {

    public static boolean isIdNumber(String var0) {
        if (var0 != null && !"".equals(var0)) {
            String var1 = "(^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)";
            boolean var2 = var0.matches(var1);
            int length = 18;
            if (var2 && var0.length() == length) {
                try {
                    char[] var3 = var0.toCharArray();
                    int[] var4 = new int[]{7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
                    String[] var5 = new String[]{"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};
                    int var6 = 0;

                    int var8;
                    for (int var7 = 0; var7 < var4.length; ++var7) {
                        var8 = Integer.parseInt(String.valueOf(var3[var7]));
                        int var9 = var8 * var4[var7];
                        var6 += var9;
                    }

                    char var11 = var3[17];
                    var8 = var6 % 11;
                    return var5[var8].toUpperCase().equals(String.valueOf(var11).toUpperCase());
                } catch (Exception var10) {
                    var10.printStackTrace();
                    return false;
                }
            } else {
                return var2;
            }
        } else {
            return false;
        }
    }

}
