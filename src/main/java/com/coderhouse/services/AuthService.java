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

    @org.springframework.transaction.annotation.Transactional
    public String register(String username, String password, Role role, String email, String nombre, Long clienteId) {
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("El usuario ya existe");
        }
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("El email ya está registrado");
        }
        if (role != null && role != Role.CLIENTE) {
            throw new IllegalArgumentException("Solo se permite registro público de clientes");
        }

        // Si se provee clienteId explícito, usar ese; si no, crear Cliente automáticamente
        com.coderhouse.models.Cliente cliente;
        if (clienteId != null) {
            cliente = clienteRepository.findById(clienteId)
                    .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado con ID: " + clienteId));
        } else {
            // Auto-crear el perfil de cliente al momento del registro
            String[] partes = (nombre != null ? nombre.trim() : username).split("\\s+", 2);
            com.coderhouse.models.Cliente nuevo = new com.coderhouse.models.Cliente();
            nuevo.setNombre(partes[0]);
            nuevo.setApellido(partes.length > 1 ? partes[1] : "");
            nuevo.setEmail(email);
            nuevo.setIne(Math.abs(java.util.UUID.randomUUID().hashCode()));
            nuevo.setNumCliente("C-" + username.toLowerCase());
            nuevo.setPuntosTotales(0);
            cliente = clienteRepository.save(nuevo);
        }

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
