package org.springframework.safe.security.authentication;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.safe.security.enmus.Security;
import org.springframework.safe.security.validate.filter.UsernameAuthenticationFilter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsUtils;

import javax.annotation.Resource;

/**
 * 表单登陆配置
 * @author Sir.D
 */
@Component
@Data
public class FromAuthenticationConfig {
    @Autowired
    public AuthenticationSuccessHandler pcAuthenticationSuccessHandler;
    @Autowired
    public AuthenticationFailureHandler pcAuthenticationFailureHandler;

    public void configure(HttpSecurity http) throws Exception {
        http.cors()
            .and().csrf().disable().authorizeRequests().requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
            .and()
                .headers().frameOptions().disable()
            .and()
                .formLogin()
                .loginPage(Security.DEFAULT_UNAUTHENTICATION_URL.message())
                .loginProcessingUrl(Security.DEFAULT_SIGN_IN_PROCESSING_URL_FORM.message())
                .successHandler(pcAuthenticationSuccessHandler)
                .failureHandler(pcAuthenticationFailureHandler);
    }

}
