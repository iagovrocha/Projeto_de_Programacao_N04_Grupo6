package br.com.unit.sistema.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.unit.sistema.app.controller.dto.NotificacaoColetaDTO;
import br.com.unit.sistema.app.controller.dto.NotificacaoDTO;
import br.com.unit.sistema.app.controller.dto.NotificacaoLidaDTO;
import br.com.unit.sistema.app.controller.dto.NotificacaoListagemDTO;
import br.com.unit.sistema.app.services.NotificacaoService;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/notificacao")
public class NotificacaoController {

    @Autowired
    private NotificacaoService service;

    @GetMapping
    @Transactional(readOnly = true)
    public Page<NotificacaoListagemDTO> listarNotificacao(Pageable paginacao){
       return service.coletarNotificacao(paginacao);
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public NotificacaoListagemDTO mostrarNotificacaoEspecifica(@PathVariable long id){
        return service.exibirNotificacaoEspecifica(id);
    }

    @GetMapping("/byUser")
    @Transactional(readOnly = true)
    public List<NotificacaoListagemDTO> listarNotificacaoUsuario(@RequestBody @Valid NotificacaoColetaDTO dados){
       return service.coletarNotificacaoUsuario(dados);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<NotificacaoDTO> criarNotificacao(@RequestBody @Valid NotificacaoDTO dados){ 
        return service.salvarNotificacao(dados); 
    }

    @PutMapping
    @Transactional
    public ResponseEntity<NotificacaoLidaDTO> marcarComoLida(@RequestBody @Valid NotificacaoLidaDTO dados){
        return service.atualizarNotificacao(dados);
    }

    @GetMapping("/teste")
    public String teste(){
        return "Ok";
    }    
}
