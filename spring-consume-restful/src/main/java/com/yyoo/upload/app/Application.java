package com.yyoo.upload.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.yyoo.domain.Quote;

@SpringBootApplication
public class Application {
	private static final Logger logger = 
			LoggerFactory.getLogger(Application.class);
	/*
	 public static void main(String[] args){
		RestTemplate restTemplate = new RestTemplate();
		Quote quote = restTemplate.getForObject("http://gturnquist-quoters.cfapps.io/api/random", Quote.class);
		logger.info("Quote: {}", quote.toString());
	} 
	 */

	public static void main(String[] args){
		SpringApplication.run(Application.class);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder){
		return builder.build();
	}
	
	@Bean
	public CommandLineRunner run(RestTemplate restTemplate){
		return args -> {
			Quote quote = restTemplate.getForObject(
					"http://gturnquist-quoters.cfapps.io/api/random", Quote.class);
			logger.info("Quote: {}", quote.toString());
		};
	}
	
}
