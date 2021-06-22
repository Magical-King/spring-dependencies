package org.springframework.logger.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.logger.service.OperatorConsequence;
import org.springframework.logger.service.impl.DefaultOperatorConsequence;
import org.springframework.logger.service.impl.DefaultOperatorLogger;
import org.springframework.logger.service.OperatorLogger;

/**
 * @author Sir.D
 */
@Configuration
public class LogConfig {

    @Bean
    @ConditionalOnMissingBean(OperatorLogger.class)
    public OperatorLogger operatorLogger() {
        return new DefaultOperatorLogger();
    }

    @Bean
    @ConditionalOnMissingBean(OperatorConsequence.class)
    public OperatorConsequence operatorConsequence() {
        return new DefaultOperatorConsequence();
    }
}
