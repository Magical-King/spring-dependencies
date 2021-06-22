package org.springframework.safe.security.validate.code;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Sir.D
 */
@Data
public class ValidateCode implements Serializable {
    private static final long serialVersionUID = 1588203828504660915L;

    private String code;

    private String type;

    private int expire;

    private LocalDateTime expireTime;

    public ValidateCode() {}

    public ValidateCode(String code, int expireIn) {
        this.code = code;
        this.expire = expireIn;
        this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
    }

    public ValidateCode(String code, LocalDateTime expireTime) {
        this.code = code;
        this.expireTime = expireTime;
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expireTime);
    }
}
