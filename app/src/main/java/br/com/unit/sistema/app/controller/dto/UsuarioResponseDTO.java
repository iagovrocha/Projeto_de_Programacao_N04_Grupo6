package br.com.unit.sistema.app.controller.dto;

import br.com.unit.sistema.app.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UsuarioResponseDTO (
    @NotNull
    Long id,
    @NotBlank
    String nome,
    @NotBlank
    @Email
    String email,
    @NotNull
    Role role){
}