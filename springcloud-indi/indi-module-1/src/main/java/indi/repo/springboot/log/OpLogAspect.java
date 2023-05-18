package indi.repo.springboot.log;

import indi.repo.springboot.log.process.OpLogJoinPointProcessor;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
@Order(1)
public class OpLogAspect {

    private final OpLogJoinPointProcessor joinPointProcessor;

    @Pointcut("@annotation(indi.repo.springboot.log.annotation.OpLog)")
    public void controllerAllMethod() {

    }

    @Around("controllerAllMethod()")
    public Object process(ProceedingJoinPoint jp) throws Throwable {
        return joinPointProcessor.process(jp);
    }

}
