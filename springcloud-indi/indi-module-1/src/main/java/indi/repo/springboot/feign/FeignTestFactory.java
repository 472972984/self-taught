package indi.repo.springboot.feign;

/**
 * @author ChenHQ
 * @date: create in 2021/6/27
 */

import feign.hystrix.FallbackFactory;
import indi.repo.common.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static indi.repo.common.exception.BaseException.CALLBACK_CODE;


/**
 * 异常处理的工厂类
 * @author admin
 */
@Component
@Slf4j
public class FeignTestFactory<T> implements FallbackFactory<T> {

    @Override
    public T create(Throwable cause) {
        if (cause.getMessage() != null) {
            log.error("feign接口调用异常,{}", cause.getMessage());
        }
        return (T) new BaseException(CALLBACK_CODE, cause.getMessage());
    }
}