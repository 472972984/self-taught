package indi.repo.springboot.toexcel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author ChenHQ
 * @date 2025/11/3 16:36
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelCollection {

    String prefix() default "";

    int maxItems() default 10; // 最大展开条目数
}