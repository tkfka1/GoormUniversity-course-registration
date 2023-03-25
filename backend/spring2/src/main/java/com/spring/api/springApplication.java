package com.spring.api;

import com.spring.api.config.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(AppConfig.class)
@SpringBootApplication
public class springApplication {

    public static void main(String[] args) {
        SpringApplication.run(springApplication.class, args);
    }

}
