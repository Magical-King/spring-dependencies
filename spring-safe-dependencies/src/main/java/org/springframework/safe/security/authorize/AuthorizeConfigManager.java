package org.springframework.safe.security.authorize;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

/**
 * 授权信息管理器
 * 收集系统中所有 AuthorizeConfigProvider 并加载配置
 * @author Sir.D
 */
public interface AuthorizeConfigManager {

    /**
     * config
     * @param config
     */
    void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config);

}



