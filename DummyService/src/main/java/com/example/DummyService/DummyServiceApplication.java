package com.example.DummyService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableFeignClients
public class DummyServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DummyServiceApplication.class, args);
	}

}
