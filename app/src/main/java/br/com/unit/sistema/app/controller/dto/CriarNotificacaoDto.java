package br.com.unit.sistema.app.controller.dto;

import br.com.unit.sistema.app.entity.TipoNotificacao;

public record CriarNotificacaoDto(TipoNotificacao tipo, String titulo, String mensagem, Long remetenteId, Long destinatarioId) {
}
