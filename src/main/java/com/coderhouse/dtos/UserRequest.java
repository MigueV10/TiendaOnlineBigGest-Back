package com.coderhouse.dtos;

import com.coderhouse.models.Role;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRequest {
    private String username;
    private String password;
    private String email;
    private String nombre;
    private Boolean activo;
    private Role role;
    private Long clienteId;
}
