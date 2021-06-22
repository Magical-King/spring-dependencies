package org.springframework.safe.security.validate.filter;

import com.alibaba.fastjson.JSON;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.common.properties.GzrobotProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.safe.utils.message.Message;
import org.springframework.safe.utils.message.Status;
import org.springframework.safe.utils.wrapper.Sm2RequestWrapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author Sir.D
 */
@Slf4j
@Component("sm2Filter")
public class Sm2Filter extends OncePerRequestFilter implements InitializingBean {

    @Autowired
    private GzrobotProperties properties;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        Sm2RequestWrapper wrapper;
        try {
            wrapper = new Sm2RequestWrapper(request, properties.getValidate().getSm2());
            chain.doFilter(wrapper, response);
        } catch (Exception e){
            e.printStackTrace();
            log.error("===> 解密失败！原因: {}", e.getMessage());
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(JSON.toJSONString(new Message(Status.FAILED, e.getMessage())));
            return;
        }

    }
}
