package br.com.unit.sistema.app.controller.dto;

import java.time.LocalDateTime;

import br.com.unit.sistema.app.entity.Notificacao;

public record NotificacaoListagemDTO(long idNotificacao, String titulo, String mensagem, boolean statusEnvio, Long idRemetente, LocalDateTime horario) {

    public NotificacaoListagemDTO(Notificacao notificacao) {
        this(notificacao.getIdNotificacao(),notificacao.getTitulo(),notificacao.getMensagem(),notificacao.isStatusEnvio(),notificacao.getIdUser(), notificacao.getDataHorarioEnvio());
    } 
}
