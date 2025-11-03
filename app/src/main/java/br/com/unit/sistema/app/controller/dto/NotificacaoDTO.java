package br.com.unit.sistema.app.controller.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.lang.Nullable;

import br.com.unit.sistema.app.entity.Tipo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NotificacaoDTO( 
    @NotBlank
    String titulo, 
    
    @NotBlank
    String mensagem, 
    
    @NotNull
    Tipo tipo,

    Long idRemetente,

    @NotNull
    List<Long> destinatarios
    
    ) {


} 
