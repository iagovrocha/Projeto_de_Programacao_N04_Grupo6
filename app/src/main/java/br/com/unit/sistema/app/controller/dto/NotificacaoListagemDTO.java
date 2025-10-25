package br.com.unit.sistema.app.controller.dto;

import br.com.unit.sistema.app.entity.Notificacao;

public record NotificacaoListagemDTO(long id, String titulo, String mensagem, boolean lida, boolean status) {

    public NotificacaoListagemDTO(Notificacao notificacao) {
        this(notificacao.getId(),notificacao.getTitulo(),notificacao.getMensagem(), notificacao.isLida(), notificacao.isStatus());

    } 
}
