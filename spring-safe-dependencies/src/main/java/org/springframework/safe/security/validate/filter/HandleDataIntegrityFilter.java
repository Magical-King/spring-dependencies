package org.springframework.safe.security.validate.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.safe.security.validate.dataintegrity.HandleDataIntegrity;
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
@Component("handleDataIntegrityFilter")
public class HandleDataIntegrityFilter extends OncePerRequestFilter implements InitializingBean {

    @Autowired
    private HandleDataIntegrity handleDataIntegrity;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String username = request.getParameter("username");
        if (StringUtils.isNotBlank(username)) {
            handleDataIntegrity.handleDataIntegrity(request.getParameter("username"));
        }
        chain.doFilter(request, response);
    }
}
