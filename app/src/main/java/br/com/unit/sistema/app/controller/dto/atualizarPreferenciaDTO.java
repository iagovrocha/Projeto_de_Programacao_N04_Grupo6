package br.com.unit.sistema.app.controller.dto;

import br.com.unit.sistema.app.entity.Tipo;
import jakarta.validation.constraints.NotNull;

public record atualizarPreferenciaDTO(
    @NotNull
    long idPreferencia,

    @NotNull
    Tipo tipo

) {

    
}
