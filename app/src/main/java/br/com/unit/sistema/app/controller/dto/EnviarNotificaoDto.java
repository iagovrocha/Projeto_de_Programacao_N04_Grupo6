package br.com.unit.sistema.app.controller.dto;

import br.com.unit.sistema.app.entity.TipoNotificacao;

import java.util.List;

public record EnviarNotificaoDto(TipoNotificacao tipo, String titulo, String mensagem, Long remetenteId, List<Long> idsDestinatarios) {
}
