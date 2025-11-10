package br.com.unit.sistema.app.controller.dto;

import br.com.unit.sistema.app.entity.Role;
import jakarta.validation.constraints.Email;

public record AtualizarUsuarioDTO(
    String nome,
    @Email
    String email,
    String senha,
    Role role
) {
}
