package org.springframework.safe.security.validate.code;

import org.springframework.safe.security.enmus.Validate;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author Sir.D
 */
public interface ValidateCodeRepository {

    /**
     * 保存验证码
     * @param request
     * @param code
     * @param type
     */
    void save(ServletWebRequest request, ValidateCode code, Validate type);

    /**
     * 获取验证码
     * @param request
     * @param type
     * @return
     */
    ValidateCode get(ServletWebRequest request, Validate type);


    /**
     * 移除验证码
     * @param request
     * @param type
     */
    void remove(ServletWebRequest request, Validate type);

}
