package org.springframework.safe.security.validate.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.safe.security.validate.code.ValidateCodeGenerator;
import org.springframework.safe.security.validate.code.email.DefaultEmailCodeSender;
import org.springframework.safe.security.validate.code.email.EmailCodeSender;
import org.springframework.safe.security.validate.code.image.ImageCodeGenerator;
import org.springframework.safe.security.validate.code.sms.DefaultSmsCodeSender;
import org.springframework.safe.security.validate.code.sms.SmsCodeSender;
import org.springframework.safe.security.validate.dataintegrity.DefaultHandleDataIntegrity;
import org.springframework.safe.security.validate.dataintegrity.HandleDataIntegrity;
import org.springframework.safe.security.validate.ipwhite.DefaultHandleIpWhite;
import org.springframework.safe.security.validate.ipwhite.HandleIpWhite;

/**
 * @author Sir.D
 */
@Configuration
public class ValidateCodeBeanConfig {

    @Bean
    @ConditionalOnMissingBean(name = "imageValidateCodeGenerator")
    public ValidateCodeGenerator imageValidateCodeGenerator() {
        ImageCodeGenerator codeGenerator = new ImageCodeGenerator();
        return codeGenerator;
    }

    @Bean
    @ConditionalOnMissingBean(SmsCodeSender.class)
    public SmsCodeSender smsCodeSender() {
        return new DefaultSmsCodeSender();
    }

    @Bean
    @ConditionalOnMissingBean(EmailCodeSender.class)
    public EmailCodeSender emailCodeSender() {
        return new DefaultEmailCodeSender();
    }

    @Bean
    @ConditionalOnMissingBean(HandleDataIntegrity.class)
    public HandleDataIntegrity handleDataIntegrityFilter() {
        return new DefaultHandleDataIntegrity();
    }

    @Bean
    @ConditionalOnMissingBean(HandleIpWhite.class)
    public HandleIpWhite handleIpWhiteFilter() {
        return new DefaultHandleIpWhite();
    }

}
