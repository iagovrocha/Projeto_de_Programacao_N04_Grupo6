package br.com.unit.sistema.app.controller.dto;

import java.time.LocalDateTime;

import br.com.unit.sistema.app.entity.Notificacao;
import br.com.unit.sistema.app.entity.Tipo;

public record NotificacaoListagemDTO(long idNotificacao, String titulo, String mensagem, boolean statusEnvio, Long idRemetente, LocalDateTime horario, Tipo tipo) {

    public NotificacaoListagemDTO(Notificacao notificacao) {
        this(notificacao.getIdNotificacao(),notificacao.getTitulo(),notificacao.getMensagem(),notificacao.isStatusEnvio(),notificacao.getIdUser(), notificacao.getDataHorarioEnvio(), notificacao.getTipo());
    } 
}
