package indi.repo.openapi.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author ChenHQ
 * @date   2021年12月21日
 */
@Component
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
public @interface OpenApiParam {

    /**
     * 对应 method 标识："hde.order.item.get"
     * @return
     */
    String value();

    /**
     * rpc远程调用实际入参
     * @return
     */
    Class paramRpcClass();

}
