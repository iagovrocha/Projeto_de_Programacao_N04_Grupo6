package br.com.unit.sistema.app.controller.dto;

import br.com.unit.sistema.app.entity.Notificacao;

public record NotificacaoListagemDTO(long idNotificacao, String titulo, String mensagem, boolean status, long idRemetente) {

    public NotificacaoListagemDTO(Notificacao notificacao) {
        this(notificacao.getIdNotificacao(),notificacao.getTitulo(),notificacao.getMensagem(),notificacao.isStatus(),notificacao.getIdRemetente());
    } 
}
