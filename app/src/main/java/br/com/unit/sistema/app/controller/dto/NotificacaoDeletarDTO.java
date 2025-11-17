package br.com.unit.sistema.app.controller.dto;

import jakarta.validation.constraints.NotNull;

public record NotificacaoDeletarDTO(
    @NotNull
    long idNotificacao) {
    
}
