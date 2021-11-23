package indi.repo.springboot.common.log.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author ChenHQ
 * @title: ImportantField
 * @description: 标识是否是重要字段注解
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ImportantField {

    /**
     * 字段释义
     * @return
     */
    String title() default "";

}
