package org.springframework.safe.security.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * 验证码生成器
 * @author Sir.D
 */
public interface ValidateCodeGenerator {

    /**
     * 验证码生成器
     * @param request
     * @return
     */
    ValidateCode generator(ServletWebRequest request);

}
