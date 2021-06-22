package org.springframework.common.properties.email;

import lombok.Data;

/**
 * @author Sir.D
 */
@Data
public class EmailSender {
    /**
     * 发件人账号
     */
    private String account = "admin@gzrobot.com";

    /**
     * 发件人账号
     */
    private String password = "gzrobot@123456";

}
