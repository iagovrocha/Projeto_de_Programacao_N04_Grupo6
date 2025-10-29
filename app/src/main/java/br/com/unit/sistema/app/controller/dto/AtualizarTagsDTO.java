package br.com.unit.sistema.app.controller.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record AtualizarTagsDTO(    
    @NotNull
    Long idTag,

    String nomeTag,
    
    @Pattern(regexp = "^#([A-Fa-f0-9]{6})$")
    String corTag,
    
    Boolean ativo) {

}
