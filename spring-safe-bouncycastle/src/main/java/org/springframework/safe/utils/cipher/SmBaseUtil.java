package org.springframework.safe.utils.cipher;

import lombok.NoArgsConstructor;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Security;

/**
 * @author Sir.D
 */
@NoArgsConstructor
public class SmBaseUtil {
    static {
        Security.addProvider(new BouncyCastleProvider());
    }
}
