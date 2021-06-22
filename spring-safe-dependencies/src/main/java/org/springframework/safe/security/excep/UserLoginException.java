package org.springframework.safe.security.excep;

import org.springframework.security.core.AuthenticationException;

/**
 * @author Sir.D
 */
public class UserLoginException extends AuthenticationException {

    public UserLoginException(String msg) {
        super(msg);
    }

}
