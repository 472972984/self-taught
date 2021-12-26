package indi.repo.openapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 功能说明: 项目启动类
 *
 * @author: ChenHQ
 * @date 2021/12/16
 * @desc:
 */
@SpringBootApplication
@EnableFeignClients(basePackages = {"indi.repo.*"})
public class OpenApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpenApiApplication.class, args);
    }


}
