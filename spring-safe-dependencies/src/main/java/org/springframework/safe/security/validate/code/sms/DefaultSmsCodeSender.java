package org.springframework.safe.security.validate.code.sms;

import lombok.extern.slf4j.Slf4j;

/**
 * 默认的短信发送器
 * @author Sir.D
 */
@Slf4j
public class DefaultSmsCodeSender implements SmsCodeSender {

    @Override
    public void send(String mobile, String code) {
        log.warn("====> Please configure a real SMS sender(SmsCodeSender) !");
        log.info("send code: {}, to mobile {}" , code , mobile);
    }
}
