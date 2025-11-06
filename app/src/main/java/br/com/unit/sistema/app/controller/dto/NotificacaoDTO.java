package br.com.unit.sistema.app.controller.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.lang.Nullable;

import br.com.unit.sistema.app.entity.Evento;
import br.com.unit.sistema.app.entity.Pagamentos;
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
    List<Long> destinatarios,

    Long idTag
    
    ) {

    public NotificacaoDTO(Pagamentos dados, Tipo tipo){
        this("Novo Pagamento Realizado", 
        "Pagamento: "+dados.getIdPagamento()+
        "Valor: "+dados.getValor()+
        "Data"+dados.getDataPagamento(), 
        tipo,
        null,
        Arrays.asList(dados.getIdUsuario()),
        null);
        }
    
    public NotificacaoDTO(Evento dados, Tipo tipo){
        this("Novo Evento registrado",
        "Evento: "+dados.getNome() +
        "Local: "+dados.getLocal() + 
        "ID_Evento: "+dados.getId(), 
        tipo,
        null,
        Arrays.asList(dados.getIdUser()),
        null);
    }

} 
