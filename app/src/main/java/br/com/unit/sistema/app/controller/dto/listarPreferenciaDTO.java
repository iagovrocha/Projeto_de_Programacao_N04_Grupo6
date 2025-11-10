package br.com.unit.sistema.app.controller.dto;

import br.com.unit.sistema.app.entity.Preferencia;
import br.com.unit.sistema.app.entity.Tipo;
import jakarta.validation.constraints.NotNull;

public record listarPreferenciaDTO(

    @NotNull
    long idPreferencia,

    @NotNull
    long idUser,

    @NotNull
    Tipo tipo
){

    public listarPreferenciaDTO(Preferencia dados){
        this(dados.getIdPreferencia(), dados.getIdUser(), dados.getTipo());
    }
    
}
