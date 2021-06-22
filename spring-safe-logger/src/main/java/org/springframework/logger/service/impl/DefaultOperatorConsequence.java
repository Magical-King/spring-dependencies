package org.springframework.logger.service.impl;

import org.springframework.logger.service.OperatorConsequence;

/**
 * @author Sir.D
 */
public class DefaultOperatorConsequence implements OperatorConsequence {
    @Override
    public boolean success(Object object) {
        return true;
    }
}
