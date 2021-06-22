package org.springframework.logger.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.logger.OperatorLog;
import org.springframework.logger.annotation.IRequestMapping;
import org.springframework.logger.annotation.Log;
import org.springframework.logger.service.OperatorConsequence;
import org.springframework.logger.service.OperatorLogger;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Map;

/**
 * @author Sir.D
 */
@Aspect
@Component
@Lazy(false)
public class LogAspect {
    private static final String LOG_URI= "/gz-log/list";

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private OperatorLogger logger;

    @Autowired
    private OperatorConsequence operatorConsequence;

    @Pointcut("@annotation(org.springframework.logger.annotation.Log)")
    private void cutMethod() {
    }

    /**
     * 环绕通知：灵活自由的在目标方法中切入代码
     */
    @Around("cutMethod()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        // 查看日志本身不记录
        String uri = request.getRequestURI();
        if (uri.endsWith(LOG_URI)) {
            return this;
        }

        OperatorLog log = new OperatorLog();
        log.setIsException(false);
        log.setOriginate(1);
        log.setRequestURI(request.getRequestURI());
        log.setServerAddr(request.getServerName());
        log.setRemoteAddr(request.getRemoteAddr());
        log.setRemoteHost(request.getRemoteHost());
        log.setRemotePort(request.getRemotePort());
        log.setContentType(request.getContentType());
        log.setCreateDate(LocalDateTime.now());

        // 获取方法名
        String methodName = joinPoint.getSignature().getName();
        log.setMethodName(methodName);

        // 获取请求方法参数
        Object[] params = joinPoint.getArgs();
        log.setMethodParameter(Arrays.toString(params));

        // log注解中的内容
        Log logAnnotation = getLogAnnotation(joinPoint);
        log.setEventCategory(logAnnotation.eventCategory());
        log.setEventType(logAnnotation.eventType());
        log.setEventLevel(logAnnotation.level());

        // IRequestMapping注解
        IRequestMapping requestAnnotation = getRequestAnnotation(joinPoint);
        if (null != requestAnnotation) {
            log.setEventCategory(String.valueOf(requestAnnotation.id() / 100 * 100));
        }

        // 执行源方法,计入开始结束时间
        long startTime = System.currentTimeMillis();
        Object proceed =null;
        try {
            proceed = joinPoint.proceed();
            // 拼装message
            buildMessage(log, logAnnotation, params);
            consequence(log, proceed);

            // 获取方法返回
            log.setMethodReturn(JSON.toJSONString(proceed));
            long endTime = System.currentTimeMillis();
            log.setProcessTime(endTime - startTime);
            this.logger.save(log);
            return proceed;
        } catch (Exception e) {
            log.setException(e);
            log.setIsException(true);
            // 拼装message
            buildMessage(log, logAnnotation, params);
            consequence(log, false);
            // 获取方法返回
            log.setMethodReturn(JSON.toJSONString(proceed));
            long endTime = System.currentTimeMillis();
            log.setProcessTime(endTime - startTime);
            this.logger.save(log);
            throw e;
        }
    }

    private void buildMessage(OperatorLog log, Log logAnnotation, Object[] requestParams) {
        String[] params = logAnnotation.params();
        String[] value = new String[params.length];
        for (int i = 0; i < requestParams.length; i++) {
            try {
                Map<String, String> map = JSON.parseObject(JSON.toJSONString(requestParams[i]),new TypeReference<Map<String,String>>(){});
                for (int j = 0; j < params.length; j++) {
                    value[j] = map.get(params[j]);
                }
            } catch (Exception e) {
                for (int j = 0; j < params.length; j++) {
                    value[j] = String.valueOf(requestParams[j]);
                }
            }
        }

        String message = logAnnotation.message();
        if (ArrayUtils.isNotEmpty(value)) {
            log.setMessage(MessageFormat.format(message, value));
        } else {
            log.setMessage(message);
        }
    }


    void consequence(OperatorLog log, Object proceed) {
        Boolean success;

        if (proceed instanceof Boolean) {
            success = (Boolean) proceed;
        } else if (null != proceed){
            success = this.operatorConsequence.success(proceed);
        } else {
            success = true;
        }

        if (success) {
            log.setIsSuccess(true);
            log.setMessage(log.getMessage() + ",执行结果是【成功】");
        } else {
            log.setIsSuccess(false);
            log.setMessage(log.getMessage() + ",执行结果是【失败】");
        }

    }

    /**
     * 获取日志注解
     * @param joinPoint
     * @return
     * @throws NoSuchMethodException
     */
    public IRequestMapping getRequestAnnotation(ProceedingJoinPoint joinPoint) throws NoSuchMethodException {
        return getDeclaredAnnotation(joinPoint, IRequestMapping.class);
    }

    /**
     * 获取日志注解
     * @param joinPoint
     * @return
     * @throws NoSuchMethodException
     */
    public Log getLogAnnotation(ProceedingJoinPoint joinPoint) throws NoSuchMethodException {
        return getDeclaredAnnotation(joinPoint, Log.class);
    }

    /**
     * 获取方法中声明的注解
     *
     * @param joinPoint
     * @return
     * @throws NoSuchMethodException
     */
    public <T extends Annotation> T getDeclaredAnnotation(ProceedingJoinPoint joinPoint, Class<T> annotationClass) throws NoSuchMethodException {
        // 获取方法名
        String methodName = joinPoint.getSignature().getName();
        // 反射获取目标类
        Class<?> targetClass = joinPoint.getTarget().getClass();
        // 拿到方法对应的参数类型
        Class<?>[] parameterTypes = ((MethodSignature) joinPoint.getSignature()).getParameterTypes();
        // 根据类、方法、参数类型（重载）获取到方法的具体信息
        Method objMethod = targetClass.getMethod(methodName, parameterTypes);
        // 拿到方法定义的注解信息
        T annotation = objMethod.getDeclaredAnnotation(annotationClass);
        // 返回
        return annotation;
    }
}
