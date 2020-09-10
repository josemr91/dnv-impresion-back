package com.dnv.impresion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class ImpresionApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImpresionApplication.class, args);
	}

}
