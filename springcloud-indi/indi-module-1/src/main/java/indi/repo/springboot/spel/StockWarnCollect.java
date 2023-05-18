package indi.repo.springboot.spel;

import java.lang.annotation.*;

/**
 * @author ChenHQ
 * @date 2023/5/18 16:12
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface StockWarnCollect {

    /** 客户id */
    String studentId();

    /** 来源 */
    String studentName();

    String time();

}