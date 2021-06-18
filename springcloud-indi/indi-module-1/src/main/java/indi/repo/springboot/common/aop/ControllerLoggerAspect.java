package indi.repo.springboot.common.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * 功能说明:
 *
 * @author: ChenHQ
 * @date: 2021/6/18
 * @desc:
 */
@Aspect
@Configuration
@Slf4j
public class ControllerLoggerAspect {

    @Pointcut("execution(public * indi.repo.springboot.controller..*(..))")
    public void controllerPoint() {
    }


    @Before("controllerPoint()")
    public void doBefore(JoinPoint point) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        String param = Arrays.toString(point.getArgs());
        String requestUrl = request.getRequestURI();
        log.info("Request url:{}, Request arguments:{}", requestUrl, param);
    }

    @AfterReturning(pointcut = "controllerPoint()", returning = "obj")
    public void AfterReturning(Object obj) {
        // 处理完请求，返回内容
        if (null == obj) {
            log.info("Response result is : null");
        } else {
            log.info("Response result is  : {}", obj.toString());
        }
    }


}
