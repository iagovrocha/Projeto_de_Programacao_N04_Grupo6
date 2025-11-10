package br.com.unit.sistema.app.controller.dto;

import java.time.LocalDateTime;

import br.com.unit.sistema.app.entity.Notificacao;
import br.com.unit.sistema.app.entity.Tipo;

public record NotificacaoUsuarioListDTO(long idNotificacao, String titulo, String mensagem, boolean statusEnvio, Long idRemetente, LocalDateTime horario, boolean lida, Tipo tipo) {
     public NotificacaoUsuarioListDTO(Notificacao notificacao, boolean lida) {
        this(notificacao.getIdNotificacao(),notificacao.getTitulo(),notificacao.getMensagem(),notificacao.isStatusEnvio(),notificacao.getIdUser(), notificacao.getDataHorarioEnvio(), lida, notificacao.getTipo());
    } 
}
