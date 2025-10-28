package br.com.unit.sistema.app.controller.dto;

import br.com.unit.sistema.app.entity.Tipo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NotificacaoDTO( 
    @NotBlank
    String titulo, 
    
    @NotBlank
    String mensagem, 
    
    @NotNull
    Tipo tipo
    
    
    ) {

} 
