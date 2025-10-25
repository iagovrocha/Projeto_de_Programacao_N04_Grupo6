package br.com.unit.sistema.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import br.com.unit.sistema.app.controller.dto.CreateTagsDTO;
import br.com.unit.sistema.app.entity.Tags;
import br.com.unit.sistema.app.repository.TagsRepositorys;

@Service
public class TagsService {

    @Autowired
    TagsRepositorys repositorys;

    @Transactional
    public void create(CreateTagsDTO dados) {
        if (repositorys.existsByNomeTags(dados.nomeTags())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Tag com esse nome já existe");
        }
        repositorys.save(new Tags(dados));
    }
    
}
