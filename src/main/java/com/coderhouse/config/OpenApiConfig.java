package com.coderhouse.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenApiConfig {

    @Value("${server.port:8080}")
    private String serverPort;

    @Bean
    OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API REST - BIGGEST Tienda Online")
                        .version("1.0.0")
                        .description("API de Clientes, Productos y Ventas para BIGGEST")
                        .contact(new Contact()
                                .name("Miguel Eduardo Urena Nieto")
                                .email("vesper1098@gmail.com")
                                .url("https://github.com/MigueV10"))
                        .license(new License()
                                .name("Licencia")
                                .url("https://github.com/MigueV10"))
                )
                .servers(List.of(
                        new Server()
                                .url("http://localhost:" + serverPort)
                                .description("Servidor Local")
                ));
    }
}
