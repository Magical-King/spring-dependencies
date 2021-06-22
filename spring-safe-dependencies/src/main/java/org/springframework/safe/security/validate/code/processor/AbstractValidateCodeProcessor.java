package org.springframework.safe.security.validate.code.processor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.safe.security.enmus.Validate;
import org.springframework.safe.security.validate.code.*;
import org.springframework.safe.security.validate.code.exception.ValidateCodeException;
import org.springframework.safe.utils.HttpUtil;
import org.springframework.web.context.request.ServletWebRequest;

import java.io.IOException;
import java.util.Map;

/**
 * 验证码抽象类
 * @author Sir.D
 */
@Slf4j
public abstract class AbstractValidateCodeProcessor<T extends ValidateCode> implements ValidateCodeProcessor {

    @Autowired
    private Map<String, ValidateCodeGenerator> validateCodeGenerators;

    @Autowired
    private ValidateCodeRepository validateCodeRepository;

    @Override
    public void create(ServletWebRequest request) throws Exception {
        T validateCode = generate(request);
        save(request, validateCode);
        send(request, validateCode);
    }

    @Override
    public void validate(ServletWebRequest request) throws ValidateCodeException, IOException {
        Validate type = type();
        this.check(request);
        validateCodeRepository.remove(request, type);
    }

    @Override
    public void check(ServletWebRequest request) throws ValidateCodeException, IOException {
        Validate type = type();
        T validateCode = (T)validateCodeRepository.get(request, type);

        String json = HttpUtil.json(request.getRequest());
        JSONObject jsonObject = JSON.parseObject(json);
        String parameter = String.valueOf(jsonObject.get(type.getParamNameOnValidate()));

        if (StringUtils.isBlank(parameter)){
            validateCodeRepository.remove(request, type);
            throw new ValidateCodeException("9900");
        }

        if (validateCode == null || validateCode.isExpired()) {
            validateCodeRepository.remove(request, type);
            throw new ValidateCodeException("9901");
        }

        if (!StringUtils.equals(validateCode.getCode(), parameter)) {
            validateCodeRepository.remove(request, type);
            throw new ValidateCodeException("9902");
        }
    }

    /**
     * 生成验证码
     * @return
     */
    private T generate(ServletWebRequest request) {
        String type = type().toString().toLowerCase();
        String generateName = type + ValidateCodeGenerator.class.getSimpleName();
        ValidateCodeGenerator validateCodeGenerator = validateCodeGenerators.get(generateName);
        if (null == validateCodeGenerator) {
            throw new ValidateCodeException("====> validate Code Generator【" + generateName + "】is non-existent！" );
        }

        return (T) validateCodeGenerator.generator(request);
    }

    /**
     * 保存验证码
     * @param request
     * @param validateCode
     */
    private void save(ServletWebRequest request, T validateCode) {
        validateCodeRepository.save(request, validateCode, type());
    }

    /**
     * 获取验证码的类型
     * @return
     */
    private Validate type() {
        String type = StringUtils.substringBefore(getClass().getSimpleName(),"CodeProcessor").toUpperCase();
        return Validate.valueOf(type);
    }

    /**
     * 发送，由子类实现
     * @param request
     * @param validateCode
     * @throws Exception
     */
    protected abstract void send(ServletWebRequest request, T validateCode) throws Exception;

}
