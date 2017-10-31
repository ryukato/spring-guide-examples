package com.yyoo.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages="com.yyoo.controller")
public class HelloWorldConfiguration {
	public static void main(String[] args){
		SpringApplication.run(HelloWorldConfiguration.class, args);
	}
}
