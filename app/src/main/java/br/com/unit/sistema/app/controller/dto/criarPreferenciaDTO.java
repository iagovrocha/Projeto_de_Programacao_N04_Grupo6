package br.com.unit.sistema.app.controller.dto;

import br.com.unit.sistema.app.entity.Tipo;
import jakarta.validation.constraints.NotNull;

public record criarPreferenciaDTO(
    @NotNull
    long idUser, 
    
    @NotNull
    Tipo tipo
    ) {    
    
}
