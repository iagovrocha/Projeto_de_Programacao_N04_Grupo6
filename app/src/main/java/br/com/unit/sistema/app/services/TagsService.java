package br.com.unit.sistema.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import br.com.unit.sistema.app.controller.dto.AtualizarTagsDTO;
import br.com.unit.sistema.app.controller.dto.CreateTagsDTO;
import br.com.unit.sistema.app.controller.dto.ListagemTagsDTO;
import br.com.unit.sistema.app.entity.Tags;
import br.com.unit.sistema.app.repository.TagsRepositorys;

@Service
public class TagsService {

    @Autowired
    private TagsRepositorys repositorys;

    @Transactional
    public void create(CreateTagsDTO dados) {
        if (repositorys.existsByNomeTag(dados.nomeTag())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Tag com esse nome já existe");
        }
        repositorys.save(new Tags(dados));
    }

    @Transactional(readOnly = true)
    public Page<ListagemTagsDTO> listarAllTags(Pageable paginacao) {
        return repositorys.findAll(paginacao).map(ListagemTagsDTO::new);
    }

    @Transactional
    public void atualizar(AtualizarTagsDTO dados) {
        if (repositorys.existsByNomeTag(dados.nomeTag())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Não é possivel Atualizar a pois Tag com esse nome já existe");
        }
        Tags tag = repositorys.getReferenceById(dados.idTag());
        tag.atualizarInformacoes(dados);
    }

    @Transactional
    public void inativarTag(Long idTag) {
        Tags tag = repositorys.getReferenceById(idTag);
        tag.inativar();
    }
}
