package indi.repo.springboot.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author ChenHQ
 * @date 2023/5/24 19:20
 */
@ConfigurationProperties(prefix = "server")
@Data
public class DemoProperties {

    @Data
    public static class ChqClass {
        String port;
    }

    ChqClass work = new ChqClass();


}
