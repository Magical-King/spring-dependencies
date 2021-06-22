package org.springframework.safe.security.validate.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Properties;

/**
 * @author Sir.D
 */
@PropertySource(value = {"classpath:kaptcha.properties"})
@Configuration
@Data
public class CaptchaConfig {

    @Value("${kaptcha.border}")
    private String border;

    @Value("${kaptcha.border.color}")
    private String borderColor;

    @Value("${kaptcha.image.width}")
    private String imageWidth;

    @Value("${kaptcha.image.height}")
    private String imageHeight;

    @Value("${kaptcha.session.key}")
    private String sessionKey;

    @Value("${kaptcha.textproducer.font.color}")
    private String fontColor;

    @Value("${kaptcha.textproducer.font.size}")
    private String fontSize;

    @Value("${kaptcha.textproducer.font.names}")
    private String fontNames;

    @Value("${kaptcha.textproducer.char.length}")
    private String charLength;

    @Bean(name = "captchaProducer")
    public DefaultKaptcha defaultKaptcha(){
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();

        Properties properties = new Properties();
        properties.setProperty("kaptcha.border",border);
        properties.setProperty("kaptcha.border.color",borderColor);
        properties.setProperty("kaptcha.image.width",imageWidth);
        properties.setProperty("kaptcha.image.height",imageHeight);
        properties.setProperty("kaptcha.session.key",sessionKey);
        properties.setProperty("kaptcha.textproducer.font.color",fontColor);
        properties.setProperty("kaptcha.textproducer.font.size",fontSize);
        properties.setProperty("kaptcha.textproducer.font.names",fontNames);
        properties.setProperty("kaptcha.textproducer.char.length",charLength);

        defaultKaptcha.setConfig(new Config(properties));
        return defaultKaptcha;
    }

}
