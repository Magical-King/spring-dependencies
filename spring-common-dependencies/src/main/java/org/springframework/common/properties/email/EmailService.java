package org.springframework.common.properties.email;

import lombok.Data;

/**
 * @author Sir.D
 */
@Data
public class EmailService {

    /**
     * 邮件发送失败重试次数
     */
    private int tryNumber = 2;

    /**
     * 是否输出调试信息
     */
    private boolean debug = true;

    /**
     * 是否启用邮件发送功能
     */
    private boolean enable = false;
    /**
     * 每一轮次发送完毕之后休眠时间（秒）
     */
    private int sleepSeconds = 15;

    /**
     * 两封邮件之间的休眠时间（秒）
     */
    private int turnOnWaitSeconds = 1;


}
