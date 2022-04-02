package indi.repo.common.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Objects;

/**
 * 功能说明:
 *
 * @author: ChenHQ
 * @date: 2021/6/18
 */
@Aspect
@Component
@Slf4j
public class ControllerLoggerAspect {

    @Pointcut("execution(public * indi.repo.*.controller..*(..))")
    public void controllerPoint() {
    }


    @Before("controllerPoint()")
    public void doBefore(JoinPoint point) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();

        String param = Arrays.toString(point.getArgs());
        String requestUrl = request.getRequestURI();
        log.info("Request url:{}, Request arguments: --> {}", requestUrl, param);
    }

    @AfterReturning(pointcut = "controllerPoint()", returning = "obj")
    public void AfterReturning(Object obj) {
        // 处理完请求，返回内容
        if (null == obj) {
            log.info("Response result is : null");
        } else {
            log.info("Response result is: <-- {}", obj);
        }
    }


}
