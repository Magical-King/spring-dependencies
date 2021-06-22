package org.springframework.safe.server;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.safe.utils.Sm4Util;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.regex.Pattern;

/**
 * @author Sir.D
 */
@Configuration
public class Sm4PasswordEncoder implements PasswordEncoder {

    public Sm4PasswordEncoder() {
    }

    @Override
    public String encode(CharSequence rawPassword) {
        return Sm4Util.encryptByEcb(rawPassword.toString());
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encode(rawPassword).equals(encodedPassword);
    }
}
