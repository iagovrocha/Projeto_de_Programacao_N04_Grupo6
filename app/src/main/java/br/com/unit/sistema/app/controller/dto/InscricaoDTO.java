package br.com.unit.sistema.app.controller.dto;

import jakarta.validation.constraints.NotNull;

public record InscricaoDTO(
    @NotNull(message = "ID do usuário é obrigatório")
    Long idUsuario,
    
    @NotNull(message = "ID do evento é obrigatório")
    Long idEvento
) {
}
