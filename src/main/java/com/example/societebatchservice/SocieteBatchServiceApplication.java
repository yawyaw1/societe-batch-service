package com.example.societebatchservice;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
@EnableBatchProcessing
public class SocieteBatchServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SocieteBatchServiceApplication.class, args);
	}

}
