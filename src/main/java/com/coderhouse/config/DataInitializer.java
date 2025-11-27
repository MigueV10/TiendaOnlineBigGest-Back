package com.coderhouse.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.coderhouse.models.Role;
import com.coderhouse.models.User;
import com.coderhouse.repositories.UserRepository;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDefaultAdmin(UserRepository userRepository, PasswordEncoder encoder) {
        return args -> {
            if (!userRepository.existsByUsername("admin")) {
                User admin = User.builder()
                        .username("admin")
                        .password(encoder.encode("admin123"))
                        .email("admin@example.com")
                        .nombre("Administrador")
                        .role(Role.ADMINISTRADOR)
                        .build();
                userRepository.save(admin);
            }
        };
    }
}
