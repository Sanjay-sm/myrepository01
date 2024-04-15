package com.springphonewebapp.springphonebookapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringPhonebookAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringPhonebookAppApplication.class, args);
		
	}
	
	

}
