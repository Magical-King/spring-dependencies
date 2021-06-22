package org.springframework.safe.security.validate.code;

import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.context.request.ServletWebRequest;

import java.io.IOException;

/**
 * 验证码处理器
 * @author Sir.D
 */
public interface ValidateCodeProcessor {

    /**
     * 创建验证码
     * @param request
     * @throws Exception
     */
    void create(ServletWebRequest request) throws Exception;


    /**
     * 验证->删除
     * @param request
     * @throws ServletRequestBindingException
     * @throws IOException
     */
    void validate(ServletWebRequest request) throws ServletRequestBindingException, IOException;

    /**
     * 验证->不删除
     * @param request
     * @throws ServletRequestBindingException
     * @throws IOException
     */
    void check(ServletWebRequest request) throws ServletRequestBindingException, IOException;

}
