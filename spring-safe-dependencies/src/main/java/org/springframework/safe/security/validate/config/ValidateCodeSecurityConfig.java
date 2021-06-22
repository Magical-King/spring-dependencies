package org.springframework.safe.security.validate.config;

import org.bouncycastle.math.ec.ECPointMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.common.properties.GzrobotProperties;
import org.springframework.safe.security.validate.filter.HandleDataIntegrityFilter;
import org.springframework.safe.security.validate.filter.Sm2Filter;
import org.springframework.safe.security.validate.filter.ValidateCodeFilter;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;

/**
 * 过滤器配置
 * @author Sir.D
 */
@Component("validateCodeSecurityConfig")
public class ValidateCodeSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private Filter ipWhiteFilter;

    @Autowired
    private Filter headerFilter;

    @Autowired
    private Filter sm2Filter;

    @Autowired
    private Filter handleDataIntegrityFilter;

    @Autowired
    private Filter validateCodeFilter;


    @Autowired
    private GzrobotProperties config;


    @Override
    public void configure(HttpSecurity builder) throws Exception {
        builder.addFilterBefore(ipWhiteFilter, AbstractPreAuthenticatedProcessingFilter.class);
        builder.addFilterBefore(headerFilter, AbstractPreAuthenticatedProcessingFilter.class);
        builder.addFilterBefore(sm2Filter, AbstractPreAuthenticatedProcessingFilter.class);
        builder.addFilterBefore(handleDataIntegrityFilter, AbstractPreAuthenticatedProcessingFilter.class);
        if (config.getValidate().getEnable()) {
            builder.addFilterBefore(validateCodeFilter, AbstractPreAuthenticatedProcessingFilter.class);
        }
    }

}
