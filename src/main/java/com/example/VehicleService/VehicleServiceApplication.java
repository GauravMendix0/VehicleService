package com.example.VehicleService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VehicleServiceApplication {

	public static void main(String[] args) {
		System.out.println("Starting Vehicle Service Application...");
		SpringApplication.run(VehicleServiceApplication.class, args);
	}

}
