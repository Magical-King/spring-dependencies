package org.springframework.safe.security.validate.code.sms;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.safe.security.enmus.Security;
import org.springframework.safe.security.validate.code.ValidateCode;
import org.springframework.safe.security.validate.code.processor.AbstractValidateCodeProcessor;
import org.springframework.safe.utils.HttpUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 短信处理器
 * @author Sir.D
 */
@Component("smsValidateCodeProcessor")
public class SmsCodeProcessor extends AbstractValidateCodeProcessor<ValidateCode> {

    @Autowired
    private SmsCodeSender smsCodeSender;

    @Override
    protected void send(ServletWebRequest request, ValidateCode validateCode) throws Exception {
        String json = HttpUtil.json(request.getRequest());
        JSONObject jsonObject = JSON.parseObject(json);
        String mobile = String.valueOf(jsonObject.get(Security.DEFAULT_MOBILE_CODE_PARAMTER.message()));

        String code = validateCode.getCode();
        this.smsCodeSender.send(mobile, code);
    }
}
