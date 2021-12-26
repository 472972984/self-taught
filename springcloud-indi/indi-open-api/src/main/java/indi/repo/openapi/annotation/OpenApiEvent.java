package indi.repo.openapi.annotation;

import java.lang.annotation.*;

/**
 * @author ChenHQ
 * @date   2021年12月21日
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
public @interface OpenApiEvent {

    /**
     * 对应 method 标识：{"hde.order.item.get"}
     * @return
     */
    String[] value();

}
