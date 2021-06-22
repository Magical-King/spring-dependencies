package org.springframework.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.common.properties.GzrobotProperties;
import org.springframework.common.properties.ValidateProperties;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Sir.D
 */
@ComponentScan(basePackages = {"org.springframework.common.**"})
@EnableConfigurationProperties(GzrobotProperties.class)
public class AutoConfiguration {

}
