package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class FatherSonDaughterApplication {

	public static void main(String[] args){
		ApplicationContext context = SpringApplication.run(FatherSonDaughterApplication.class, args);
	}

}
