package org.springframework.safe.security.validate.code.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author Sir.D
 */
public class ValidateCodeException extends AuthenticationException {

    public ValidateCodeException(String msg) {
        super(msg);
    }

}
