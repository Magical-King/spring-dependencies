package org.springframework.safe.utils.password;

import org.springframework.safe.core.excep.SpringSafeException;

/**
 * @author Sir.D
 */
public interface PasswordStrategy {

    /**
     * 返回密码
     * @param var0
     * @param var1
     * @return
     * @throws SpringSafeException
     */
    String password(String var0, int var1) throws SpringSafeException;

}
