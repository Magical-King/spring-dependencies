package org.springframework.safe.utils.password.impl;

import org.springframework.safe.core.excep.SpringSafeException;
import org.springframework.safe.utils.password.PasswordStrategy;

/**
 * 默认值密码
 * @author Sir.D
 */
public class DefaultPassword implements PasswordStrategy {

    @Override
    public String password(String var0, int var1) throws SpringSafeException {
        return "GZrobot123456+";
    }
}
