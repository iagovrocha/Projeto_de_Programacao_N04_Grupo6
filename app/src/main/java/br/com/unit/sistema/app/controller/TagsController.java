package br.com.unit.sistema.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.unit.sistema.app.controller.dto.AtualizarTagsDTO;
import br.com.unit.sistema.app.controller.dto.CreateTagsDTO;
import br.com.unit.sistema.app.controller.dto.ListagemTagsDTO;
import br.com.unit.sistema.app.services.TagsService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/tags")
public class TagsController {

    @Autowired
    TagsService tagsService;

    @PostMapping
    public ResponseEntity<CreateTagsDTO> createTag(@RequestBody @Valid CreateTagsDTO data) {
        tagsService.create(data);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public Page<ListagemTagsDTO> listarTags(@PageableDefault(size=10) Pageable paginacao) {
        return tagsService.listarAllTags(paginacao);
    }

    @PutMapping
    public void atualizarTag(@RequestBody @Valid AtualizarTagsDTO dados) {
        tagsService.atualizar(dados);
    }

    @DeleteMapping("/{idTag}")
    public void removerTag(@PathVariable Long idTag) {
        tagsService.inativarTag(idTag);
    }
}
