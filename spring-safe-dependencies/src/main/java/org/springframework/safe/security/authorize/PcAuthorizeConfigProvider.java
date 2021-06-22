package org.springframework.safe.security.authorize;

import org.springframework.core.annotation.Order;
import org.springframework.safe.security.enmus.Security;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/**
 * 核心授权配置提供器，安全模块设计的url在此配置
 * @author Sir.D
 */
@Component
@Order(Integer.MIN_VALUE)
public class PcAuthorizeConfigProvider implements AuthorizeConfigProvider {

    @Override
    public boolean config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        config.antMatchers(
                Security.DEFAULT_UNAUTHENTICATION_URL.message(),
                Security.DEFAULT_UNAUTHENTICATION_URL_PREFIX.message(),
                "/druid/**",
                "/swagger-ui.html", "/swagger-resources/**", "/v2/api-docs",
                "/oauth/**",
                "/actuators","/actuator/**",
                "/favicon.ico",
                "/singleStation/websocket/socketServer",
//                "/websocket/socketServer",
                "/safe/**",
                "/singleStation/logout",
                "/**/logout",
//                "/html/**",
                "/**/**.html",

                "/crypto-js/**",
                "/audit/**",
                "/css/**",
                "/file/**",
                "/fonts/**",
                "/images/**",
                "/favicon.ico",
                "/index.html",
                "/img/**",
                "/js/**",
                "/upload/**",
                "/video/**",
                "/static/**"
//                ,"/**/**"
        ).permitAll();

        return false;
    }

}
