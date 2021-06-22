package org.springframework.safe.security.enmus;

/**
 * @author Sir.D
 */
public enum Validate {

    /**
     * 短信验证码
     */
    SMS {
        @Override
        public String getParamNameOnValidate() {
            return Security.DEFAULT_CODE_SMS.message();
        }
    },

    /**
     * 图片验证码
     */
    IMAGE {
        @Override
        public String getParamNameOnValidate() {
            return Security.DEFAULT_CODE_IMAGE.message();
        }
    },

    /**
     * 邮箱验证码
     */
    EMAIL {
        @Override
        public String getParamNameOnValidate() {
            return Security.DEFAULT_CODE_EMAIL.message();
        }
    };

    /**
     * 校验时从请求中获取的参数的名字
     */
    public abstract String getParamNameOnValidate();
}
