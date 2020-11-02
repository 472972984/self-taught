package com.self.elasticsearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.zxp.esclientrhl.annotation.EnableESTools;


@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableESTools
/**
 * @author Chenhq
 */
public class ElasticSearchApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElasticSearchApplication.class, args);
	}

}
