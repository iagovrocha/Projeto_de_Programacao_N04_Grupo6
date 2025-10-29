package br.com.unit.sistema.app.controller.dto;

import jakarta.validation.constraints.NotNull;

public record NotificacaoLidaDTO(
    @NotNull
    long idNotificacao,
    
    @NotNull
    long idUser) {
}
