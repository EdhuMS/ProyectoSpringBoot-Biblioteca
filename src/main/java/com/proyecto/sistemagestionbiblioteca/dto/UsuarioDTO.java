package com.proyecto.sistemagestionbiblioteca.dto;

import lombok.Data;

@Data
public class UsuarioDTO {
    private Long id;
    private String username;
    private String password;
}