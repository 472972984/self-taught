package indi.repo.springboot.common.log.annotation;

import indi.repo.springboot.common.log.enums.OptTypeEnum;

import java.lang.annotation.*;

/**
 * 单表修改新旧值日志记录
 * 被注解修饰的方法，修改提交的数据需要放在第一位
 * @author admin
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface UpdateEntityLog {

    /**
     * 模块名称
     */
    String type();

    /**
     * 操作类型
     */
    OptTypeEnum optType();

    /**
     * 表id — SPEL 表达式
     */
    String id();

    /**
     * 单表修改的dao层Class类型
     */
    Class<?> modifyClass();

    /**
     * 用户Id
     */
    String userId() default "";

    /**
     * 用户名
     */
    String username() default "";
}
