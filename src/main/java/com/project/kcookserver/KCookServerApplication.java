package com.project.kcookserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class KCookServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(KCookServerApplication.class, args);
	}

}
