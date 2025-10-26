package br.com.unit.sistema.app.controller.dto;

import br.com.unit.sistema.app.entity.TipoNotificacao;

public record NotificacaoResponseDto (Long id, TipoNotificacao tipo, String titulo, String mensagem, Long remetenteId) {
}
