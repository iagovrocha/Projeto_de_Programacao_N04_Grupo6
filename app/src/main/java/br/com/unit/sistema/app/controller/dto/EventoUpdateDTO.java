package br.com.unit.sistema.app.controller.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EventoUpdateDTO(
    @NotBlank
    String nome,
    
    @NotBlank
    String local,
    
    @NotNull
    LocalDateTime data
) {
}
