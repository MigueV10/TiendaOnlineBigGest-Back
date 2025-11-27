package com.coderhouse.dtos;

import com.coderhouse.models.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private String nombre;
    private Role role;
    private Boolean activo;
    private Long clienteId;
}
