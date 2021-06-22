package org.springframework.safe.security.validate.filter;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.common.properties.GzrobotProperties;
import org.springframework.safe.security.validate.ipwhite.HandleIpWhite;
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
@Component("ipWhiteFilter")
public class IpWhiteFilter extends OncePerRequestFilter implements InitializingBean {

    @Autowired
    private HandleIpWhite handleIpWhite;

    @Autowired
    private GzrobotProperties properties;

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (properties.getIpWhite().getEnable()) {
            handleIpWhite.validate(request);
        }
        chain.doFilter(request, response);
    }
}
