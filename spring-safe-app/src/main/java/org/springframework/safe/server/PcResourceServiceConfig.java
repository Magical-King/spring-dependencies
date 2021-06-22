package org.springframework.safe.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.safe.security.authentication.FromAuthenticationConfig;
import org.springframework.safe.security.authentication.sms.SmsCodeAuthenticationSecurityConfig;
import org.springframework.safe.security.authorize.AuthorizeConfigManager;
import org.springframework.safe.security.enmus.Security;
import org.springframework.safe.security.validate.config.ValidateCodeSecurityConfig;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;

/**
 * 资源管理配置
 * @author Sir.D
 */
@Configuration
@EnableWebSecurity
public class PcResourceServiceConfig extends WebSecurityConfigurerAdapter {
    private static final String REMEBER_COOKIE_NAME = "REMEBER_COOKIE_NAME";
    private static final int REMEBER_TIME = 1*60*60;

    @Resource
    private FromAuthenticationConfig fromAuthenticationConfig;

    @Resource
    private AccessDeniedHandler pcAccessDeniedHandler;

    @Resource
    private LogoutSuccessHandler pcLogoutSuccessHandler;

    @Resource
    private AuthenticationEntryPoint pcAuthenticationEntryPointHandler;

    @Resource
    private SessionInformationExpiredStrategy pcExpiredSessionStrategy;

    @Resource
    private AuthorizeConfigManager authorizeConfigManager;

    @Resource
    private DataSource dataSource;

    @Resource
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;

    @Resource
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Resource
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        fromAuthenticationConfig.configure(http);
        http.exceptionHandling()
            .authenticationEntryPoint(pcAuthenticationEntryPointHandler);

        http.apply(validateCodeSecurityConfig)
            .and()
                .apply(smsCodeAuthenticationSecurityConfig)
            .and()
                .headers().frameOptions().disable()
            .and()
                .exceptionHandling().accessDeniedHandler(pcAccessDeniedHandler)
            .and()
                .logout()
                .deleteCookies("JSESSIONID")
                .logoutSuccessHandler(pcLogoutSuccessHandler)
                .permitAll()
//                .and()
//                .rememberMe()
//                .rememberMeCookieName(REMEBER_COOKIE_NAME)
//                .tokenValiditySeconds(REMEBER_TIME)
//                .tokenRepository(persistentTokenRepository())
            .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .sessionFixation().migrateSession()
//                .invalidSessionUrl(Security.DEFAULT_UNAUTHENTICATION_URL.message())
                .maximumSessions(1)
                .maxSessionsPreventsLogin(false)
                .expiredSessionStrategy(pcExpiredSessionStrategy)
            ;

        authorizeConfigManager.config(http.authorizeRequests());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        AuthenticationManager manager = super.authenticationManagerBean();
        return manager;
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /**
     * 数据库记住我持久化
     * @return
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
        db.setDataSource(dataSource);
        return db;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
        auth.userDetailsService(userDetailsService).passwordEncoder(new Sm4PasswordEncoder());
    }

}
