package indi.repo.multisource.aop;

import indi.repo.multisource.config.DBTypeEnum;
import indi.repo.multisource.config.SourceTypeAnnotation;
import indi.repo.multisource.config.DbContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 功能说明:
 *
 * @author: ChenHQ
 * @date: 2021/6/7
 * @desc:
 */
@Component
@Order(value = -100)
@Slf4j
@Aspect
public class DataSourceAspect {
    /**
     * 设置路径下的文件都使用这个数据源
     */
    //设置该路径下dao使用first数据源
    @Pointcut("execution(* com.twy.druid.dao.first..*.*(..))")
    private void firstAspect() {
    }
    //设置该路径下dao使用second数据源
    @Pointcut("execution(* com.twy.druid.dao.second..*.*(..))")
    private void secondAspect() {
    }

    @Before("dataSourcePointCut()")
    public void first() {
        log.info("切换到first数据源...");
        DbContextHolder.setDbType(DBTypeEnum.first);
    }

    @Before("secondAspect()")
    public void second() {
        log.info("切换到second数据源...");
        DbContextHolder.setDbType(DBTypeEnum.second);
    }

    /**
     * 切点: 所有配置 DataSourceAn 注解的方法
     */
    @Pointcut("@annotation(indi.repo.multisource.config.SourceTypeAnnotation)")
    public void dataSourcePointCut() {
    }

    @Around("dataSourcePointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        SourceTypeAnnotation ds = method.getAnnotation(SourceTypeAnnotation.class);
        // 通过判断 DataSource 中的值来判断当前方法应用哪个数据源
        DbContextHolder.setDbType(ds.value());
        log.info("AOP切换数据源成功，数据源为：" + ds.value());
        log.info("设置数据源为：" + ds.value());
        try {
            return point.proceed();
        } finally {
            DbContextHolder.clearDbType();
            log.info("clean datasource");
        }
    }
}

