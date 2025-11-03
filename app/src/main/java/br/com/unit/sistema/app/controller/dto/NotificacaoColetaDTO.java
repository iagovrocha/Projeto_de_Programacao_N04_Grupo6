package br.com.unit.sistema.app.controller.dto;

import br.com.unit.sistema.app.entity.Tipo;
import jakarta.validation.constraints.NotNull;

public record NotificacaoColetaDTO(
    long idUser,
    Tipo tipo
    
    ){
    
}
