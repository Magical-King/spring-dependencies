package org.springframework.safe.security.validate.code.processor;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.safe.security.enmus.Security;
import org.springframework.safe.security.enmus.Validate;
import org.springframework.safe.security.validate.code.ValidateCode;
import org.springframework.safe.security.validate.code.ValidateCodeRepository;
import org.springframework.safe.security.validate.code.exception.ValidateCodeException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author Sir.D
 */
@Component
@ConditionalOnProperty(value = {"gzrobot.validate.cache.redis"})
public class ValidateCodeRedisRepository implements ValidateCodeRepository {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void save(ServletWebRequest request, ValidateCode code, Validate type) {
        String key = buildKey(request, type);
        redisTemplate.opsForValue().set(key, JSON.toJSONString(code), code.getExpire(), TimeUnit.SECONDS);
    }

    @Override
    public ValidateCode get(ServletWebRequest request, Validate type) {
        Object value = redisTemplate.opsForValue().get(buildKey(request, type));
        if (value == null) {
            return null;
        }
        return JSON.parseObject((String) value, ValidateCode.class);
    }

    @Override
    public void remove(ServletWebRequest request, Validate type) {
        redisTemplate.delete(buildKey(request, type));
    }

    private String buildKey(ServletWebRequest request, Validate type) {
        try {
            String id = "ID";
            String sign = ":";
            if (StringUtils.isNotBlank(request.getHeader(id))) {
                id = request.getHeader(id);
            }

            return Security.DEFAULT_CODE_KEY.message() + sign + type.toString() + sign + id;
        } catch (ValidateCodeException e) {
            e.printStackTrace();
            return null;
        }
    }
}
