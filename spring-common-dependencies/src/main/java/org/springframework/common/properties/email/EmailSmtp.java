package org.springframework.common.properties.email;

import lombok.Data;

/**
 * @author Sir.D
 */
@Data
public class EmailSmtp {

    /**
     * 服务器启用用户认证
     */
    private boolean auth = true;

    /**
     * 邮件服务器机器名，有时候为了避免反垃圾邮件系统的服务器名称验证需要设成其它名字
     */
    private String localhost = "gzrobotWeb";

    /**
     * 邮件服务器地址
     */
    private String host = "127.0.0.1";

    /**
     * 邮件服务器端口
     */
    private int port = 25;

}
