package org.springframework.logger;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Sir.D
 */
@Data
public class OperatorLog {

    /**
     * 异常传递
     */
    private Exception exception;

    /**
     * 是否成功
     */
    private Boolean isSuccess;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 日志等级
     * 0:正常
     * 1:一般
     * 2：严重
     * 3：危机
     */
    private int eventLevel;

    /**
     * 监听日志分类，属于模块名
     */
    private String eventCategory;

    /**
     * 监听日志类型
     */
    private String eventType;

    /**
     * 内容
     */
    private String message;

    /**
     * 异常状态码
     */
    private Integer exceptionCode;

    /**
     * 0:false, 1:true
     */
    private Boolean isException;

    /**
     * 请求的 URI
     */
    private String requestURI;

    /**
     * 请求来源 ( 1: 普通, 2: 对接, 3: 内部 )
     */
    private Integer originate;

    /**
     * 服务地址
     */
    private String serverAddr;

    /**
     * 远程地址
     */
    private String remoteAddr;

    /**
     * 远程主机
     */
    private String remoteHost;

    /**
     * 远程端口
     */
    private Integer remotePort;

    /**
     * 内容类型
     */
    private String contentType;

    /**
     * 请求的服务方法名称
     */
    private String methodName;

    /**
     * 方法参数
     */
    private String methodParameter;

    /**
     * 请求参数
     */
    private String requestParameter;

    /**
     * 方法返回类型
     */
    private String methodReturn;

    /**
     * 创建时间
     */
    private LocalDateTime createDate;

    /**
     * 执行时间
     */
    private Long processTime;

}


