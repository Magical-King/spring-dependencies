package org.springframework.safe.security.validate.code.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.safe.security.enmus.Security;
import org.springframework.safe.security.validate.code.ValidateCode;
import org.springframework.safe.security.validate.code.processor.AbstractValidateCodeProcessor;
import org.springframework.safe.security.validate.code.sms.SmsCodeSender;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 短信处理器
 * @author Sir.D
 */
@Component("emailValidateCodeProcessor")
public class EmailCodeProcessor extends AbstractValidateCodeProcessor<ValidateCode> {

    @Autowired
    private EmailCodeSender emailCodeSender;

    @Override
    protected void send(ServletWebRequest request, ValidateCode validateCode) throws Exception {
        String username = ServletRequestUtils.getRequiredStringParameter(request.getRequest(), Security.DEFAULT_USERNAME_PARAMTER.message());
        String password = ServletRequestUtils.getRequiredStringParameter(request.getRequest(), Security.DEFAULT_PASSWORD_PARAMTER.message());
        String code = validateCode.getCode();
        this.emailCodeSender.send(username, password, code);
    }
}
