package org.springframework.safe.security.validate.code.image;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.springframework.safe.security.validate.code.ValidateCode;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * 图片验证码
 * @author Sir.D
 */
@Data
public class ImageCode extends ValidateCode {

    @JSONField(serialize = false)
    private BufferedImage image;

    public ImageCode(BufferedImage image, String code, int expireIn) {
        super(code, expireIn);
        this.image = image;
    }

    public ImageCode(BufferedImage image, String code, LocalDateTime expireTime) {
        super(code, expireTime);
        this.image = image;
    }

}
