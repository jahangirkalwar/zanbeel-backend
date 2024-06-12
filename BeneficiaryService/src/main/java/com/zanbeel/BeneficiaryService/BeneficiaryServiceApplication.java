package com.zanbeel.BeneficiaryService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableFeignClients
public class BeneficiaryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BeneficiaryServiceApplication.class, args);
	}
}
