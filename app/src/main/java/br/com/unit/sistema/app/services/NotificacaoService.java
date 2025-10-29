package br.com.unit.sistema.app.services;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;

import br.com.unit.sistema.app.controller.dto.NotificacaoColetaDTO;
import br.com.unit.sistema.app.controller.dto.NotificacaoDTO;
import br.com.unit.sistema.app.controller.dto.NotificacaoLidaDTO;
import br.com.unit.sistema.app.controller.dto.NotificacaoListagemDTO;
import br.com.unit.sistema.app.entity.Notificacao;
import br.com.unit.sistema.app.entity.NotificacaoUsuario;
import br.com.unit.sistema.app.entity.NotificacaoUsuarioID;
import br.com.unit.sistema.app.repository.NotificacaoRepository;
import br.com.unit.sistema.app.repository.NotificacaoUsuarioRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Service
public class NotificacaoService{
    
    @Autowired
    private NotificacaoRepository repository;

    @Autowired
    private NotificacaoUsuarioRepository notificacaoUsuarioRepository;
    
    //GET
    public Page<NotificacaoListagemDTO> coletarNotificacao(@PageableDefault Pageable paginacao){
        return repository.findAll(paginacao).map(notificacao -> new  NotificacaoListagemDTO(notificacao));
    }

    public NotificacaoListagemDTO exibirNotificacaoEspecifica(long id){
        NotificacaoListagemDTO notificacao = new NotificacaoListagemDTO(repository.getReferenceById(id));

        return notificacao;
    }

    //exibir todas as notificacoes daquele usuario
    public List<NotificacaoListagemDTO> coletarNotificacaoUsuario(NotificacaoColetaDTO dados){
        List<NotificacaoListagemDTO> listaNotificacao = new ArrayList<>();
        for (NotificacaoUsuario not : notificacaoUsuarioRepository.findAll()){
            if(not.getIdNotificacaoUsuario().getIdUser() == dados.idUser()){
                NotificacaoListagemDTO notificacao = new NotificacaoListagemDTO(repository.getReferenceById(not.getIdNotificacaoUsuario().getIdNotificacao()));
                listaNotificacao.add(notificacao);
            }
        }
        return listaNotificacao;
    }

    //POST
    @Transactional
    public ResponseEntity<NotificacaoDTO> salvarNotificacao(@Valid NotificacaoDTO dados){
        Notificacao notificacao = new Notificacao(dados);
        repository.save(notificacao);
        for(long id : dados.destinatarios()){
            notificacaoUsuarioRepository.save(new NotificacaoUsuario(id,notificacao.getIdNotificacao()));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(dados);
    }

    //PUT
    @Transactional
    public ResponseEntity<NotificacaoLidaDTO> atualizarNotificacao(@Valid NotificacaoLidaDTO dados){
        try{
            NotificacaoUsuarioID id = new NotificacaoUsuarioID(dados);
            if (notificacaoUsuarioRepository.existsById(id) == false){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(dados);
            }
            NotificacaoUsuario notificacao = notificacaoUsuarioRepository.getReferenceById(id);
            notificacao.marcarLida();
            return ResponseEntity.ok(dados);
        }catch (Exception e ){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(dados);
        }
    }

}
