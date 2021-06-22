package org.springframework.safe.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.safe.core.excep.SpringSafeException;
import org.springframework.safe.utils.password.PasswordStrategy;
import org.springframework.safe.utils.password.impl.DefaultPassword;
import org.springframework.safe.utils.password.impl.RandomPassword;

import java.security.SecureRandom;

/**
 * @author Sir.D
 */
public class PasswordUtil {

    /**
     * 获取密码
     * @param var0 密码规则/默认值
     * @param var1 密码位数
     * @param var2 密码生成策略->true 随机密码 false 默认密码
     * @return
     * @throws Exception
     */
    public static String get(String var0, int var1, boolean var2) throws SpringSafeException {
        PasswordStrategy strategy;
        if (var2) {
            strategy = new RandomPassword();
        } else {
            strategy = new DefaultPassword();
        }

        return strategy.password(var0, var1);
    }

    /**
     * 摘要
     * @param var0 密码
     * @param var1 摘要方式 SM3/MD5
     * @return
     */
    public static String digest(String var0, String var1) {
        String var4 = "MD5";
        if (var1.equalsIgnoreCase(var4)) {
            var4 = DigestUtils.md5Hex(var0);
        } else {
            var4 = Sm3Util.digest(var0);
        }

        return var4;
    }

    public static void main(String[] args) {
        String digest = digest("GZrobot123456+", "SM3");
        System.out.println(digest);
    }

}
