package br.com.unit.sistema.app.controller.dto;

import java.math.BigDecimal;

public record InscricaoResponseDTO(
    Long idInscricao,
    Long idUsuario,
    String nomeUsuario,
    Long idEvento,
    String nomeEvento,
    BigDecimal valorPago,
    String statusPagamento,
    String mensagem
) {
}
