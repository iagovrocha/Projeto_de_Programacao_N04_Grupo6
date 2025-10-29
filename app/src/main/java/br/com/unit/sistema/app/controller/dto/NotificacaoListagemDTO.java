package br.com.unit.sistema.app.controller.dto;

import br.com.unit.sistema.app.entity.Notificacao;

public record NotificacaoListagemDTO(long idNotificacao, String titulo, String mensagem, boolean statusEnvio, long idRemetente) {

    public NotificacaoListagemDTO(Notificacao notificacao) {
        this(notificacao.getIdNotificacao(),notificacao.getTitulo(),notificacao.getMensagem(),notificacao.isStatusEnvio(),notificacao.getIdUser());
    } 
}
