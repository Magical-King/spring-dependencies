package org.springframework.safe.security.validate.code.email;

import lombok.extern.slf4j.Slf4j;
import org.springframework.safe.core.excep.SpringSafeException;
import org.springframework.safe.security.validate.code.sms.SmsCodeSender;

/**
 * 默认的短信发送器
 * @author Sir.D
 */
@Slf4j
public class DefaultEmailCodeSender implements EmailCodeSender {

    @Override
    public void send(String userId, String password, String code) throws SpringSafeException {
        log.warn("====> Please configure a real Email sender(EmailCodeSender) !");
        log.info("send code: {}, to userId {}" , code , userId);
    }
}
