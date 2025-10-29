package br.com.unit.sistema.app.controller.dto;

import jakarta.validation.constraints.NotNull;

public record PagamentoCreateDTO(
    @NotNull
    Long idUsuario,
    
    @NotNull 
    Double valor) {
    
}