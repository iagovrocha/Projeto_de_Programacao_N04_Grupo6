package br.com.unit.sistema.app.controller.dto;

import java.util.Arrays;
import java.util.List;

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
        this("Novo Pagamento Realizado ", 
        "Pagamento: "+dados.getIdPagamento()+
        " Valor: R$"+dados.getValor(),
        tipo,
        null,
        Arrays.asList(dados.getIdUsuario()),
        null);
        }
    
    public NotificacaoDTO(Evento dados, Tipo tipo){
        this("Novo Evento registrado",
        "Evento: "+dados.getNome() +
        " Local: "+dados.getLocal() + 
        " ID_Evento: "+dados.getId(), 
        tipo,
        null,
        Arrays.asList(dados.getIdUser()),
        null);
    }

    public NotificacaoDTO(Evento dados, java.util.List<Long> destinatarios, Tipo tipo){
        this("Atualização de Evento - " + dados.getNome(),
        "Evento: "+dados.getNome() +
        " Local: "+dados.getLocal() + 
        " ID_Evento: "+dados.getId(),
        tipo,
        null,
        destinatarios,
        null);
    }

    public NotificacaoDTO(Evento evento, Long idUsuario, Tipo tipo){
        this("Confirmação de Inscrição - " + evento.getNome(),
        "Sua inscrição foi confirmada com sucesso!\n\n" +
        "Evento: " + evento.getNome() + "\n" +
        "Local: " + evento.getLocal() + "\n" +
        "Data e Horário: " + evento.getData() + "\n" +
        "ID do Evento: " + evento.getId() + "\n\n" +
        "Aguardamos você!",
        tipo,
        null,
        Arrays.asList(idUsuario),
        null);
    }

} 
