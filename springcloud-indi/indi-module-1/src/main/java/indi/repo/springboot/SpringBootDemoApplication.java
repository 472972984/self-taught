package indi.repo.springboot;


import com.thebeastshop.forest.springboot.annotation.ForestScan;
import indi.repo.springboot.common.config.DemoProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 功能说明:
 *
 * @author: ChenHQ
 * @date: 2021/6/18
 */
@SpringBootApplication
//@EnableDiscoveryClient
//@EnableFeignClients
@EnableAsync
@MapperScan("indi.repo.springboot.mapper")
@ForestScan("indi.repo.springboot.forest")
@EnableConfigurationProperties({
        DemoProperties.class
})
@ServletComponentScan
@PropertySource(value = "classpath:git.properties", encoding = "UTF-8", ignoreResourceNotFound = true)
public class SpringBootDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDemoApplication.class, args);
    }


}
