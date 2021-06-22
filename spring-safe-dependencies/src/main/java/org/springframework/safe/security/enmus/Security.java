package org.springframework.safe.security.enmus;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Sir.D
 */
public enum Security {

    // 当需要身份验证是，默认跳转的url
    DEFAULT_UNAUTHENTICATION_URL_PREFIX("/auth/**"),

    // 当需要身份验证是，默认跳转的url
    DEFAULT_UNAUTHENTICATION_URL("/index.html"),

    // 默认的用户名密码url
    DEFAULT_SIGN_IN_PROCESSING_URL_FORM("/auth/form"),

    // 使用手机验证码登陆的url
    DEFAULT_SIGN_IN_PROCESSING_URL_MOBILE("/auth/mobile"),

    // 使用邮箱验证码登陆的url
    DEFAULT_SIGN_IN_PROCESSING_URL_EMAIL("/auth/email"),

    // 图片验证码-参数名
    DEFAULT_CODE_KEY("validateCode"),

    // 短信验证码-参数名
    DEFAULT_MOBILE_CODE_PARAMTER("mobile"),

    // 邮箱验证码-参数名
    DEFAULT_EMAIL_CODE_PARAMTER("email"),

    // 图片验证码-参数名
    DEFAULT_CODE_IMAGE("imageCode"),

    // 短信验证码-参数名
    DEFAULT_CODE_SMS("smsCode"),

    // 邮箱验证码-参数名
    DEFAULT_CODE_EMAIL("emailCode"),

    // 登录名-参数名
    DEFAULT_USERNAME_PARAMTER("username"),

    // 登陆密码-参数名
    DEFAULT_PASSWORD_PARAMTER("password"),
    ;


    private final String message;
    public String message(){
        return message;
    }

    Security(String message) {
        this.message = message;
    }

}
