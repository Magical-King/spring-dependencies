package org.springframework.safe.utils.cipher;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Sir.D
 */
@Data
@NoArgsConstructor
public class Sm2Cipher {
    private byte[] c1;
    private byte[] c2;
    private byte[] c3;
    private byte[] cipherText;
}
