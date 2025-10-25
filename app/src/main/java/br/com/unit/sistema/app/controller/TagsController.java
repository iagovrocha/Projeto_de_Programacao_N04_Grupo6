package br.com.unit.sistema.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.unit.sistema.app.controller.dto.CreateTagsDTO;
import br.com.unit.sistema.app.entity.Tags;
import br.com.unit.sistema.app.repository.TagsRepositorys;
import br.com.unit.sistema.app.services.TagsService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/tags")
public class TagsController {

    @Autowired
    TagsRepositorys tagsRepositorys;

    @Autowired
    TagsService tagsService;

    @PostMapping
    public ResponseEntity<CreateTagsDTO> createTag(@RequestBody @Valid CreateTagsDTO data) {
        tagsService.create(data);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public Page<Tags> listarTags(@PageableDefault(size=10) Pageable paginacao) {
        return tagsRepositorys.findAll(paginacao);
    }

    // @GetMapping("/{idTag}")
    // public ResponseEntity<TagsDTO> getTag(@PathVariable long idTag) {
    //     return new ResponseEntity<>(dummy, HttpStatus.OK);
    // }


}
