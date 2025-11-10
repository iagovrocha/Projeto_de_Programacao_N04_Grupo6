package br.com.unit.sistema.app.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UsuarioAutenticacaoDTO (
    @NotBlank
    String senha,
    @NotBlank
    @Email
    String email){
}