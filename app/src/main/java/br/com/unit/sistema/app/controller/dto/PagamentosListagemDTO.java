package br.com.unit.sistema.app.controller.dto;

import br.com.unit.sistema.app.entity.Pagamentos;

public record PagamentosListagemDTO(Long idPagamento, Long idUsuario, Double valor, Boolean status) {
    public PagamentosListagemDTO(Pagamentos dados) {
        this(dados.getIdPagamento(), dados.getIdUsuario(), dados.getValor(), dados.getStatus());
    }
}
