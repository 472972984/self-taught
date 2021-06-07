package indi.repo.multisource;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 功能说明:
 *
 * @author: ChenHQ
 * @date: 2021/6/7
 * @desc:
 */
@SpringBootApplication
@MapperScan("indi.repo.multisource.*.dao")
public class TestApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }
}
