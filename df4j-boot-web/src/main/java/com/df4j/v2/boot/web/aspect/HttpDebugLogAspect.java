package com.df4j.v2.boot.web.aspect;

import com.df4j.v2.base.util.JsonUtils;
import com.df4j.v2.boot.properties.DfBootWebProperties;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Aspect
@Component
@Order(1)
@ConditionalOnProperty(prefix = "df.boot.web", name = "open-http-debug-log",havingValue = "true")
public class HttpDebugLogAspect {
    private Logger logger = LoggerFactory.getLogger(HttpDebugLogAspect.class);

    private DfBootWebProperties dfBootWebProperties;

    private ThreadLocal<Long> startTime = new ThreadLocal<>();

    private ThreadLocal<String> url = new ThreadLocal<>();

    @Pointcut("@annotation(com.df4j.boot.web.aspect.PrintLog)" +
            " || @within(com.df4j.boot.web.aspect.PrintLog)" +
            " || execution(public * com..controller.*.*(..))" +
            " || execution(public * cn..controller.*.*(..))" +
            " || execution(public * org..controller.*.*(..))" +
            " || execution(public * net..controller.*.*(..))")
    public void httpLog() {

    }

    @Before("httpLog()")
    public void doBefore(JoinPoint joinPoint) {
        startTime.set(System.currentTimeMillis());

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        url.set(null);
        try {
            url.set(request.getRequestURL().toString());
        }catch (Exception e){
            url.set(null);
            logger.error("获取当前url异常，将url设置为null");
        }
        Object requestMap = null;
        Object[] args = joinPoint.getArgs();
        if(args != null && args.length > 0){
            for(int i = 0; i < args.length; i++){
                if(args[i] instanceof Map){
                    requestMap = args[i];
                    break;
                }
            }
        }
        logger.info("request info：url:{},method:{},ip:{},function:{},args:{}",
                request.getRequestURL(),
                request.getMethod(),
                request.getRemoteAddr(),
                joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName(),
                JsonUtils.writeObjectAsString(requestMap));

    }

    @After("httpLog()")
    public void doAfter() {
        long time = System.currentTimeMillis() - startTime.get();
        if(time >= dfBootWebProperties.getPrintErrorLogThreshold()){
            logger.error("url:{}, current request cost:{} ms", url.get(), time);
        } else if(time >= dfBootWebProperties.getPrintWarnLogThreshold()){
            logger.warn("url:{}, current request cost:{} ms", url.get(), time);
        } else {
            logger.info("url:{}, current request cost:{} ms", url.get(), time);
        }
    }

    @AfterReturning(returning = "object", pointcut = "httpLog()")
    public void afterReturning(Object object) {
        logger.info("url:{},response info:{}", url.get(), JsonUtils.writeObjectAsString(object));
    }
}