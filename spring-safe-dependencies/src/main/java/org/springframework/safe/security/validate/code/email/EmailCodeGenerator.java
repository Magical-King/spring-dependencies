package org.springframework.safe.security.validate.code.email;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.common.properties.GzrobotProperties;
import org.springframework.safe.security.validate.code.ValidateCode;
import org.springframework.safe.security.validate.code.ValidateCodeGenerator;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 短信生成器
 * @author Sir.D
 */
@Component("emailValidateCodeGenerator")
public class EmailCodeGenerator implements ValidateCodeGenerator {

    @Autowired
    private GzrobotProperties config;

    @Override
    public ValidateCode generator(ServletWebRequest request) {
        String code = RandomStringUtils.randomNumeric(config.getEmail().getNumber());
        return new ValidateCode(code, config.getEmail().getExpire());
    }

}
