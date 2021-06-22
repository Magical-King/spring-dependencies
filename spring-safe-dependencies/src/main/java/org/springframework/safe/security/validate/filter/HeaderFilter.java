package org.springframework.safe.security.validate.filter;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.common.properties.GzrobotProperties;
import org.springframework.safe.utils.message.Message;
import org.springframework.safe.utils.message.Status;
import org.springframework.safe.utils.wrapper.Sm2RequestWrapper;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Sir.D
 */
@Slf4j
@Component("headerFilter")
public class HeaderFilter extends OncePerRequestFilter implements InitializingBean {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        response.addHeader("X-Frame-Options","SAMEORIGIN");
        response.addHeader("Referer-Policy","origin");
        response.addHeader("Content-Security-Policy","object-src 'self'");
        response.addHeader("X-Permitted-Cross-Domain-Policies","master-only");
        response.addHeader("X-Content-Type-Options","nosniff");
        response.addHeader("X-XSS-Protection","1; mode=block");
        response.addHeader("X-Download-Options","noopen");
        chain.doFilter(request, response);
    }
}
