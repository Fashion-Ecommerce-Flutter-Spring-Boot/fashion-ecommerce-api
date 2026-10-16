package com.visal.FashionEcommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController 
public class FashionEcommerceApplication {

	@GetMapping("/message")
	public String getMessage(){
		return "Hello Java Spring Boot";
	}

	public static void main(String[] args) {
		SpringApplication.run(FashionEcommerceApplication.class, args);
	}

}
