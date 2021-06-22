package org.springframework.common.properties;

import lombok.Data;
import org.springframework.common.properties.email.EmailSender;
import org.springframework.common.properties.email.EmailService;
import org.springframework.common.properties.email.EmailSmtp;

/**
 * @author Sir.D
 */
@Data
public class EmailProperties {

	/**
	 * 过期时间
	 */
	private int expire = 60 * 3;

	/**
	 * 随机数长度
	 */
	private int number = 4;

	/**
	 * 默认发件地址 ， 有很多邮件服务器要求发件人地址和发件人账号必须一致，请注意
	 */
	private String defaultSender = "root@gzrobot.com";

	/**
	 * 邮件传输协议，建议不要修改
	 */
	private String transportProtocol = "smtp";

	private EmailService service;

	private EmailSmtp smtp;

	private EmailSender sender;

}
