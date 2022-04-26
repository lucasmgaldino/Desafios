package com.amaro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class DesafioBackendAmaroApplication {

	public static void main(String[] args) {
		SpringApplication.run(DesafioBackendAmaroApplication.class, args);
	}

}
