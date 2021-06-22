package org.springframework.safe.security.validate.code.email;

import org.springframework.safe.core.excep.SpringSafeException;

/**
 * @author Sir.D
 */
public interface EmailCodeSender {

    /**
     * 邮箱发送接口，先验证账号密码才能发送
     * @param userId
     * @param password
     * @param code
     * @throws SpringSafeException
     */
    void send(String userId, String password, String code) throws SpringSafeException;

}
