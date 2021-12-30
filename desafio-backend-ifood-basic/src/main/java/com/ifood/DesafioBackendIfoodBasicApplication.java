package com.ifood;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class DesafioBackendIfoodBasicApplication {

	public static void main(String[] args) {
		SpringApplication.run(DesafioBackendIfoodBasicApplication.class, args);
	}

}
