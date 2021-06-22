package org.springframework.safe.security.validate.code.sms;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.safe.security.validate.code.ValidateCode;
import org.springframework.safe.security.validate.code.ValidateCodeGenerator;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 短信生成器
 * @author Sir.D
 */
@Component("smsValidateCodeGenerator")
public class SmsCodeGenerator implements ValidateCodeGenerator {

    @Override
    public ValidateCode generator(ServletWebRequest request) {
        String code = RandomStringUtils.randomNumeric(4);
        return new ValidateCode(code, 60);
    }

}
