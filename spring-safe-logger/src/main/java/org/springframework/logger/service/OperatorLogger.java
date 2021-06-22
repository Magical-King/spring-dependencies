package org.springframework.logger.service;


import org.springframework.logger.OperatorLog;

/**
 * @author Sir.D
 */
public interface OperatorLogger {

    /**
     * 记录实体
     * @param log
     */
    void save(OperatorLog log);

}
