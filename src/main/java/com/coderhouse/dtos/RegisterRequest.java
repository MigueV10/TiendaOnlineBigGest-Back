package com.coderhouse.dtos;

import com.coderhouse.models.Role;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegisterRequest {
    private String username;
    private String password;
    private Role role;
    private String email;
    private String nombre;
    private Long clienteId;
}
