package com.example.fonet_mulakat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.example.fonet_mulakat.repo")
@EntityScan("com.example.fonet_mulakat.model")
public class FonetMulakatApplication {

	public static void main(String[] args) {
		SpringApplication.run(FonetMulakatApplication.class, args);
	}

}