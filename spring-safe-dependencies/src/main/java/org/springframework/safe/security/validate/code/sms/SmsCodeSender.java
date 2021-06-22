package org.springframework.safe.security.validate.code.sms;

/**
 * @author Sir.D
 */
public interface SmsCodeSender {

    /**
     * 短信发送接口
     * @param mobile
     * @param code
     */
    void send(String mobile, String code);

}
