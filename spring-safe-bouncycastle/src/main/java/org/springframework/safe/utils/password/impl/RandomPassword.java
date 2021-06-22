package org.springframework.safe.utils.password.impl;

import org.springframework.safe.core.excep.SpringSafeException;
import org.springframework.safe.utils.password.PasswordStrategy;

import java.security.SecureRandom;

/**
 * 随机密码
 * @author Sir.D
 */
public class RandomPassword implements PasswordStrategy {

    @Override
    public String password(String var0, int var1) throws SpringSafeException {
        byte length = 8;

        String var2;
        do {
            if (var1 < length) {
                throw new SpringSafeException(12005);
            }

            var2 = bulid(var1);
        } while(!var2.matches(var0));

        return var2;
    }


    private static String bulid(int var0) {
        char[] var1 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890~!@#$%^&*.?".toCharArray();
        StringBuffer var2 = new StringBuffer();
        SecureRandom var3 = new SecureRandom();

        for (int var4 = 0; var4 < var0; var4 ++) {
            var2.append(var1[var3.nextInt(var1.length)]);
        }

        return var2.toString();
    }
}
