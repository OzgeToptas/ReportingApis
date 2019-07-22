package com.ozge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({ "com.ozge" })
@EnableAutoConfiguration
@SpringBootApplication
public class ReportingApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReportingApiApplication.class, args);
	}

}