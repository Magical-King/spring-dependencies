package org.springframework.safe.security.authorize;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

/**
 * 授权配置提供器
 * @author Sir.D
 */
public interface AuthorizeConfigProvider {

    /**
     * 针对anyRequest的配置，在整个授权配置中，应该有且只有一个针对anyrequest的配置
     * 如果所有的实现都没有，系统会自动增加一个anyRequest().authenticated()的配置
     * 如果有多个针对anyRequest的配置则会抛出异常
     * @param config
     * @return
     */
    boolean config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config);

}
