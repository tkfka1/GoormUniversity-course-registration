package com.finalproject;

import com.finalproject.config.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(AppConfig.class)
@SpringBootApplication
public class FinalprojectApplication {

	public static void main(String[] args) {

		SpringApplication.run(FinalprojectApplication.class, args);
	}

}
