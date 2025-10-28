package br.com.unit.sistema.app.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
import br.com.unit.sistema.app.controller.dto.NotificacaoDTO;
import br.com.unit.sistema.app.controller.dto.NotificacaoLidaDTO;
import br.com.unit.sistema.app.controller.dto.NotificacaoListagemDTO;
import br.com.unit.sistema.app.entity.Notificacao;
import br.com.unit.sistema.app.repository.NotificacaoRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Service
public class NotificacaoService{
    
    @Autowired
    private NotificacaoRepository repository;

    public Page<NotificacaoListagemDTO> coletarNotificacao(@PageableDefault Pageable paginacao){
       return repository.findAll(paginacao).map(notificacao -> new  NotificacaoListagemDTO(notificacao));
    }

    @Transactional
    public ResponseEntity<NotificacaoDTO> salvarNotificacao(@Valid NotificacaoDTO dados){
        repository.save(new Notificacao(dados));

        return ResponseEntity.status(HttpStatus.CREATED).body(dados);
    }

    @Transactional
    public ResponseEntity<NotificacaoLidaDTO> atualizarNotificacao(@Valid NotificacaoLidaDTO dados){
        try{
            if (repository.existsById(dados.id()) == false){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(dados);
            }
            Notificacao notificacao = repository.getReferenceById(dados.id());
            //notificacao.marcarLida();
            return ResponseEntity.ok(dados);
        }catch (Exception e ){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(dados);
        }
    }

}
