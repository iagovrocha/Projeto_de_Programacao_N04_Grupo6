package br.com.unit.sistema.app.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UsuarioDTO (
    @NotNull
    Long id,
    @NotBlank
    String nome,
    @NotBlank
    @Email
    String email){
}