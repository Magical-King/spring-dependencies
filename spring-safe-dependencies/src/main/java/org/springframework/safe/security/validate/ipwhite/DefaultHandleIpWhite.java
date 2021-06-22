package org.springframework.safe.security.validate.ipwhite;

import org.springframework.safe.core.excep.SpringSafeException;
import org.springframework.safe.security.validate.code.exception.ValidateCodeException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Sir.D
 */
public class DefaultHandleIpWhite implements HandleIpWhite {
    @Override
    public void validate(HttpServletRequest request) throws SpringSafeException {
    }
}
