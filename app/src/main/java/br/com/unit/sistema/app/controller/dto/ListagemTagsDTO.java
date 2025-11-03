package br.com.unit.sistema.app.controller.dto;

import br.com.unit.sistema.app.entity.Tags;

public record ListagemTagsDTO(Long idTag, String nomeTag, String corTag, Boolean ativo) {
    public ListagemTagsDTO (Tags tag) {
        this(tag.getIdTag(), tag.getNomeTag(), tag.getCorTag(), tag.getAtivo());
    }
}
