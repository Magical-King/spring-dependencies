package org.springframework.safe.security.authentication.sms;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author Sir.D
 */
@Data
public class SmsCodeAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        SmsCodeAuthenticationToken authenticationToken = (SmsCodeAuthenticationToken)authentication;
        UserDetails user = userDetailsService.loadUserByUsername((String) authenticationToken.getPrincipal());

        if (null == user) {
            throw new InternalAuthenticationServiceException("===> 无法获取用户信息!");
        }

        SmsCodeAuthenticationToken authenticationTokenResult = new SmsCodeAuthenticationToken(user, user.getPassword(), user.getAuthorities());

        authenticationTokenResult.setDetails(authenticationToken.getDetails());

        return authenticationTokenResult;
    }

    /**
     * 判断是否是自定义认证token
     * @param aClass
     * @return
     */
    @Override
    public boolean supports(Class<?> aClass) {
        return SmsCodeAuthenticationToken.class.isAssignableFrom(aClass);
    }
}
