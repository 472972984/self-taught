package indi.repo.multisource.config;

import java.lang.annotation.*;

/**
 * 功能说明:
 *
 * @author: ChenHQ
 * @date: 2021/6/7
 * @desc:
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SourceTypeAnnotation {
    DBTypeEnum value() default DBTypeEnum.first;
}

