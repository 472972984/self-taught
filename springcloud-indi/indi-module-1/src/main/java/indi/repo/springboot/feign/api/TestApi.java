package indi.repo.springboot.feign.api;

import indi.repo.springboot.feign.FeignTestFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author ChenHQ
 * @date: create in 2021/6/27
 */
@FeignClient(value = "springboot-module", fallbackFactory = FeignTestFactory.class)
public interface TestApi {

    @GetMapping("/module2/test")
    String test();


}
