package com.venda.apivenda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class ApivendaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApivendaApplication.class, args);
		System.out.print(new BCryptPasswordEncoder().encode("W@e3r4t5"));
	}

}
