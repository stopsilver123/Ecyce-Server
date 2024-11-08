package com.ecyce.karma;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class KarmaApplication {

	public static void main(String[] args) {
		SpringApplication.run(KarmaApplication.class, args);
	}

}
