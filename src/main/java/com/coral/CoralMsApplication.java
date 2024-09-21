package com.coral;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info =
@Info(
		title = "Coral Microservices",
		description = "Coral Microservices API Documentation",
		version = "v1"
))
public class CoralMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoralMsApplication.class, args);
	}

}
