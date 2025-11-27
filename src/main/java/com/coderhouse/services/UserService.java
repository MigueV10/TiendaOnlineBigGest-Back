package com.coderhouse.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.coderhouse.dtos.UserRequest;
import com.coderhouse.dtos.UserResponse;
import com.coderhouse.models.Cliente;
import com.coderhouse.models.Role;
import com.coderhouse.models.User;
import com.coderhouse.repositories.ClienteRepository;
import com.coderhouse.repositories.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ClienteRepository clienteRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       ClienteRepository clienteRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.clienteRepository = clienteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserResponse> findAll() {
        return userRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public UserResponse findById(Long id) {
        return userRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
    }

    @Transactional
    public UserResponse create(UserRequest req) {
        if (userRepository.existsByUsername(req.getUsername())) {
            throw new IllegalArgumentException("El usuario ya existe");
        }
        User user = new User();
        apply(user, req, true);
        return toResponse(userRepository.save(user));
    }

    @Transactional
    public UserResponse update(Long id, UserRequest req) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        apply(user, req, false);
        return toResponse(userRepository.save(user));
    }

    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    private void apply(User user, UserRequest req, boolean isCreate) {
        if (req.getUsername() != null) user.setUsername(req.getUsername());
        if (req.getEmail() != null) user.setEmail(req.getEmail());
        if (req.getNombre() != null) user.setNombre(req.getNombre());
        if (req.getRole() != null) {
            user.setRole(req.getRole());
        } else if (isCreate && user.getRole() == null) {
            user.setRole(Role.CLIENTE);
        }
        if (req.getActivo() != null) user.setActivo(req.getActivo());

        if (req.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(req.getPassword()));
        } else if (isCreate) {
            throw new IllegalArgumentException("La contraseña es obligatoria");
        }

        if (isCreate && req.getEmail() == null) {
            throw new IllegalArgumentException("El email es obligatorio");
        }

        if (req.getClienteId() != null) {
            Cliente c = clienteRepository.findById(req.getClienteId())
                    .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));
            user.setCliente(c);
        }
    }

    private UserResponse toResponse(User u) {
        Long clienteId = u.getCliente() != null ? u.getCliente().getId() : null;
        return UserResponse.builder()
                .id(u.getId())
                .username(u.getUsername())
                .email(u.getEmail())
                .nombre(u.getNombre())
                .role(u.getRole())
                .activo(u.isActivo())
                .clienteId(clienteId)
                .build();
    }
}
