package org.springframework.safe.security.validate.dataintegrity;

import org.springframework.safe.security.validate.code.exception.ValidateCodeException;

/**
 * @author Sir.D
 */
public interface HandleDataIntegrity {

    /**
     * 验证数据完整性
     * @param userId
     * @throws ValidateCodeException
     */
    void handleDataIntegrity(String userId) throws ValidateCodeException;

}
