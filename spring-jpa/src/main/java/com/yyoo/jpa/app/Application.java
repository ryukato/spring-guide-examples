package com.yyoo.jpa.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.yyoo.jpa.dao.CustomerRepository;
import com.yyoo.jpa.model.Customer;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.yyoo.*")
@EntityScan(basePackages="com.yyoo.*")
public class Application {
	private static final Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public CommandLineRunner demo(CustomerRepository repo) {
		return (args) -> {
			// savem a couple of customers
			repo.save(new Customer("Jack", "Bauer"));
			repo.save(new Customer("Chloe", "O'Brian"));
			repo.save(new Customer("Kim", "Bauer"));
			repo.save(new Customer("David", "David"));
			repo.save(new Customer("Michelle", "Dessler"));

			// fetch all customers
			log.info("Customers found with findAll();");
			log.info("--------------------------------");
			repo.findAll().forEach(c -> {
				log.info(c.toString());
			});

			log.info("");

			// fetch an indivisual customer by ID
			Customer customer = repo.findOne(1L);
			log.info("Customer found with findOne(1L):");
			log.info("--------------------------------");
			log.info(customer.toString());
			log.info("");

			// fetch customers by last name
			log.info("Customer found with findByLastName('Bauer'):");
			log.info("--------------------------------------------");
			
			repo.findByLastName("Bauer").forEach(c -> {
				log.info(c.toString());
			});
			
			log.info("");
		};
	}
}
