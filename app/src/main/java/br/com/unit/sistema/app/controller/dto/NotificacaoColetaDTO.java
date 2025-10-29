package br.com.unit.sistema.app.controller.dto;

import jakarta.validation.constraints.NotNull;

public record NotificacaoColetaDTO(
    @NotNull
    long idUser){
    
}
