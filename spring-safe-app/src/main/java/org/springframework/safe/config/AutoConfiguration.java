package org.springframework.safe.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.safe.server.Sm4PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author Sir.D
 */
@ComponentScan(basePackages = {"org.springframework.safe.app.**","org.springframework.safe.server.**"})
@MapperScans(@MapperScan("org.springframework.uac.**.mapper"))
public class AutoConfiguration {
}
