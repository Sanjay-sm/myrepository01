package com.example.spring_cashing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SpringCashingApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCashingApplication.class, args);
	}

}
