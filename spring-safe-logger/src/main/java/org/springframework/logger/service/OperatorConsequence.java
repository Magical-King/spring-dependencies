package org.springframework.logger.service;

/**
 * @author Sir.D
 */
public interface OperatorConsequence {


    /**
     * 最终方法执行的结果状态
     * @param object
     * @return
     */
    boolean success(Object object);

}
