package org.springframework.safe.security.authentication;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 认证相关的扩展配置点，业务系统可以通过声明同类型或同名的bean来覆盖
 * @author Sir.D
 */
public class BeanConfig {

    /**
     * 默认密码处理器
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(PasswordEncoder.class)
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 默认认证器
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(DefaultUserDetailsServiceImpl.class)
    public UserDetailsService userDetailsService() {
        return new DefaultUserDetailsServiceImpl();
    }

}
