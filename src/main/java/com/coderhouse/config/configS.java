package com.coderhouse.config;
	import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

	@Configuration
	public class configS {

	    @Bean
	    OpenAPI customOpenAPI() {
	        return new OpenAPI()
	                .info(new Info()
	                        .title("API REST FULL - CRUD DE CLIENTES-PRODUCTOS-VENTAS - CODERHOUSE")
	                        .version("1.0.0")
	                        .description("UN CRUD SENCILLO DE CLIENTE, PRODUCTO Y VENTA")
	                        .contact(new Contact()
	                                .name("Miguel Eduardo Urena Nieto")
	                                .email("vesper1098@gmail.com")
	                                .url("https://coderhouse.com.ar"))
	                        .license(new License()
	                                .name("Licencia")
	                                .url("https://github.com/MigueV10"))
	                )
	                .servers(List.of(new Server()
	                        .url("http://localhost:8080")
	                        .description("Servidor Local")));

	    }
	
}

