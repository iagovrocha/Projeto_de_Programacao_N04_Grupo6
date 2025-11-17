package br.com.unit.sistema.app.controller.dto;

import br.com.unit.sistema.app.entity.MethodTypeLog;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CriarLogNotificacaoDTO(
    @NotNull
    Long idNotificacao, 
    
    Long idUser,  
    
    @NotNull
    MethodTypeLog met, 
    
    @NotBlank
    String func) {
    
}
