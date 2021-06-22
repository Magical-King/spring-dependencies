package org.springframework.safe.security.authorize;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 默认的授权配置管理器
 * @author Sir.D
 */
@Component
public class PcAuthorizeConfigManager implements AuthorizeConfigManager {

    @Autowired
    private List<AuthorizeConfigProvider> authorizeConfigProviders;

    @Override
    public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        boolean existAnyRequestConfig = false;
        String existAnyRequestConfigName = null;

        for (AuthorizeConfigProvider provider : authorizeConfigProviders) {
            boolean current = provider.config(config);
            if (existAnyRequestConfig && current) {
                throw new RuntimeException("Duplicate configuration (anyRequest) :" + existAnyRequestConfigName + ","
                        + provider.getClass().getSimpleName());
            } else if (current){
                existAnyRequestConfig = true;
                existAnyRequestConfigName = provider.getClass().getSimpleName();
            }
        }

        if (!existAnyRequestConfig) {
            config.anyRequest().authenticated();
        }
    }
}
