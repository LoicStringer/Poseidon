package com.poseidon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;



@SpringBootApplication
public class PoseidonApplication {

	public static void main(String[] args) {
		SpringApplication.run(PoseidonApplication.class, args);
	}

}
