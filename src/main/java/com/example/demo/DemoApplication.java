package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // This annotation enables Spring Boot's auto-configuration, component scanning, and configuration properties.
public class DemoApplication {

	public static void main(String[] args) {
		// This is the starting point for our Spring Boot application.
		// It creates an application context and registers all beans.
		SpringApplication.run(DemoApplication.class, args);
	}

}
