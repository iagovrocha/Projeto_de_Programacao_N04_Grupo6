package br.com.unit.sistema.app.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record EventoCreateDTO(
    @NotBlank
    String nome,
    
    @NotBlank
    String local,
    
    @NotNull
    LocalDateTime data,
    
    @NotNull
    Long idOrganizador,
    
    @PositiveOrZero(message = "O preço deve ser zero ou positivo")
    BigDecimal preco
) {
}
