package org.springframework.safe.security.validate.code.image;

import com.google.code.kaptcha.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.common.properties.GzrobotProperties;
import org.springframework.safe.security.validate.code.ValidateCode;
import org.springframework.safe.security.validate.code.ValidateCodeGenerator;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import java.awt.image.BufferedImage;

/**
 * @author Sir.D
 */
@Component("imageValidateCodeGenerator")
public class ImageCodeGenerator implements ValidateCodeGenerator {

    @Autowired
    private Producer captchaProducer;

    @Autowired
    private GzrobotProperties config;

    @Override
    public ValidateCode generator(ServletWebRequest request) {
        String kaptchaCode = captchaProducer.createText();
        BufferedImage image = captchaProducer.createImage(kaptchaCode);
        return new ImageCode(image, kaptchaCode, config.getImage().getExpire());
    }

}
