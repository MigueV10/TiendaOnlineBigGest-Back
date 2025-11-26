package com.coderhouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication

public class CrudProyectoApplication {
	

	public static void main(String[] args) {
		SpringApplication.run(CrudProyectoApplication.class, args);
	}
	 @Bean
	 public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
					.allowedOrigins(
						"https://localhost:5174",
						"https://main.d19lyjmonu04ta.amplifyapp.com"
						)
					.allowedMethods("GET","POST","PUT","PATCH","DELETE","OPTIONS")
					.allowedHeaders("*")
					.allowCredentials(true)
					.maxAge(3600);
			}
		};
	}
}
