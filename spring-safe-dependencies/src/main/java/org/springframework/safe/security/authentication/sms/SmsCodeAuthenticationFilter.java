package org.springframework.safe.security.authentication.sms;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpMethod;
import org.springframework.safe.security.enmus.Security;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Sir.D
 */
public class SmsCodeAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private boolean postOnly = true;
    /**
     * 处理过滤器要处理的请求url
     */
    public SmsCodeAuthenticationFilter() {
        super(new AntPathRequestMatcher(Security.DEFAULT_SIGN_IN_PROCESSING_URL_MOBILE.message(), HttpMethod.POST.name()));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (this.postOnly && !request.getMethod().equals(HttpMethod.POST.name())) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        } else {
            String mobile = obtainMobile(request);
            if (mobile == null) {
                mobile = "";
            }

            mobile = mobile.trim();

            SmsCodeAuthenticationToken authRequest = new SmsCodeAuthenticationToken(mobile, null);
            this.setDetails(request, authRequest);

            return this.getAuthenticationManager().authenticate(authRequest);
        }
    }

    /**
     * 获取手机号
     * @param request
     * @return
     */
    protected String obtainMobile(HttpServletRequest request) {
        return request.getParameter(Security.DEFAULT_MOBILE_CODE_PARAMTER.message());
    }

    protected void setDetails(HttpServletRequest request, SmsCodeAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

}
