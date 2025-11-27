package com.coderhouse.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.coderhouse.models.Role;
import com.coderhouse.models.User;
import com.coderhouse.repositories.ClienteRepository;
import com.coderhouse.repositories.UserRepository;
import com.coderhouse.security.JwtService;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final ClienteRepository clienteRepository;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager,
                       JwtService jwtService,
                       ClienteRepository clienteRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.clienteRepository = clienteRepository;
    }

    public String register(String username, String password, Role role, String email, String nombre, Long clienteId) {
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("El usuario ya existe");
        }

        if (role != null && role != Role.CLIENTE) {
            throw new IllegalArgumentException("Solo se permite registro público de clientes");
        }

        var cliente = clienteId != null
                ? clienteRepository.findById(clienteId)
                    .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"))
                : null;

        User user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .email(email)
                .nombre(nombre)
                .cliente(cliente)
                .role(Role.CLIENTE)
                .build();
        userRepository.save(user);

        return jwtService.generateToken(user);
    }

    public String authenticate(String username, String password) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));

        User user = (User) auth.getPrincipal();
        return jwtService.generateToken(user);
    }
}
