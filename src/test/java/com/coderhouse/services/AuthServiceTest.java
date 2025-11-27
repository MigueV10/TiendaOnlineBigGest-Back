package com.coderhouse.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.coderhouse.models.Role;
import com.coderhouse.models.User;
import com.coderhouse.repositories.ClienteRepository;
import com.coderhouse.repositories.UserRepository;
import com.coderhouse.security.JwtService;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private AuthService authService;

    private User user;

    @BeforeEach
    void setup() {
        user = User.builder()
                .id(1L)
                .username("user")
                .password("pwd")
                .role(Role.CLIENTE)
                .build();
    }

    @Test
    void register_createsUserAndReturnsToken() {
        when(userRepository.existsByUsername("user")).thenReturn(false);
        when(passwordEncoder.encode("pwd")).thenReturn("enc");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(jwtService.generateToken(any(User.class))).thenReturn("token");

        String token = authService.register("user", "pwd", Role.CLIENTE, "mail@test.com", "Name", null);

        assertEquals("token", token);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void register_duplicate_throws() {
        when(userRepository.existsByUsername("user")).thenReturn(true);

        assertThrows(IllegalArgumentException.class,
                () -> authService.register("user", "pwd", Role.CLIENTE, "mail@test.com", "Name", null));
    }

    @Test
    void register_withNonClienteRole_throws() {
        assertThrows(IllegalArgumentException.class,
                () -> authService.register("user", "pwd", Role.ADMINISTRADOR, "mail@test.com", "Name", null));
    }

    @Test
    void authenticate_returnsToken() {
        Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(auth);
        when(jwtService.generateToken(eq(user))).thenReturn("token");

        String token = authService.authenticate("user", "pwd");

        assertEquals("token", token);
    }
}
