package indi.repo.springboot.toexcel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author ChenHQ
 * @date 2025/11/3 16:35
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelExpand {

    String prefix() default ""; // 字段前缀

    boolean recursive() default true; // 是否递归展开

    int maxDepth() default 3; // 最大展开深度

}