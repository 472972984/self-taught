package indi.repo.studentservice;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 功能说明:
 *
 * @author: ChenHQ
 * @date: 2021/6/18
 * @desc:
 */
@EnableDiscoveryClient
@EnableFeignClients
@EnableAsync
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class StudentModuleApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentModuleApplication.class, args);
    }


}
