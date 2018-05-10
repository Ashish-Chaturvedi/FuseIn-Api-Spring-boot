package com.fuseIn.api.utils;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.fuseIn.api"})
public class SpringBootContainerInitializer {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootContainerInitializer.class, args);
	}
}