package org.springframework.safe.security.validate.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.common.config.AutoConfiguration;
import org.springframework.common.properties.GzrobotProperties;
import org.springframework.http.HttpMethod;
import org.springframework.safe.security.enmus.Security;
import org.springframework.safe.security.enmus.Validate;
import org.springframework.safe.security.validate.code.ValidateCodeProcessorHolder;
import org.springframework.safe.security.validate.code.exception.ValidateCodeException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Sir.D
 */
@Slf4j
@Component("validateCodeFilter")
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {

    /**
     * 验证码校验失败处理器
     */
    @Autowired
    private AuthenticationFailureHandler pcAuthenticationFailureHandler;

    @Autowired
    private GzrobotProperties config;

    /**
     * 验证码处理器
     */
    @Autowired
    private ValidateCodeProcessorHolder validateCodeProcessorHolder;

    /**
     * 验证请求url与配置的url是否匹配的工具类
     */
    private AntPathMatcher pathMatcher = new AntPathMatcher();

    /**
     * 存放所有需要校验验证码的url
     */
//    private Map<String, Validate> urlMap = new HashMap<>();
    private Map<Validate, String> urlMap = new HashMap<>();

    /**
     * 初始化需拦截的url
     * @throws ServletException
     */
    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();

//        if (config.getValidate().getType().equals(Validate.IMAGE.name())) {
//            urlMap.put(Security.DEFAULT_SIGN_IN_PROCESSING_URL_FORM.message(), Validate.IMAGE);
//        } else if (config.getValidate().getType().equals(Validate.EMAIL.name())) {
//            urlMap.put(Security.DEFAULT_SIGN_IN_PROCESSING_URL_FORM.message(), Validate.EMAIL);
//        } else if (config.getValidate().getType().equals(Validate.SMS.name())) {
//            urlMap.put(Security.DEFAULT_SIGN_IN_PROCESSING_URL_FORM.message(), Validate.SMS);
//        }

        for (Validate validate : Validate.values()) {
            urlMap.put(validate, Security.DEFAULT_SIGN_IN_PROCESSING_URL_FORM.message());
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        Validate type = type(request);

        if (null != type) {
            log.info("===> 校验请求【{}】中的验证码，验证类型为【{}】", request.getRequestURI(), type);
            try {
                validateCodeProcessorHolder.findValidateCodeProcessor(type).validate(new ServletWebRequest(request, response));
                log.info("===> 验证码通过！");
            } catch (ValidateCodeException e) {
                log.info("===> 验证码失败！");
                pcAuthenticationFailureHandler.onAuthenticationFailure(request, response, e);
                return;
            }
        }

        chain.doFilter(request, response);
    }

    private Validate type(HttpServletRequest request) {
        Validate type = null;
        if (!StringUtils.equalsIgnoreCase(request.getMethod(), HttpMethod.GET.name()) && pathMatcher.match(Security.DEFAULT_SIGN_IN_PROCESSING_URL_FORM.message(),request.getRequestURI())) {
//            Set<String> urls = urlMap.keySet();
//            for (String url : urls) {
//                if (pathMatcher.match(url, request.getRequestURI())) {
//                    type = urlMap.get(url);
//                }
//            }

            Set<Validate> validates = urlMap.keySet();
            for (Validate validate : validates) {
                String parameter = request.getParameter(validate.getParamNameOnValidate());
                if (StringUtils.isNotEmpty(parameter)) {
                    type = validate;
                    break;
                }
            }
        }



        return type;
    }

}
