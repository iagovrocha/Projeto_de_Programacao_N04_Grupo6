package br.com.unit.sistema.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.unit.sistema.app.controller.dto.NotificacaoDTO;
import br.com.unit.sistema.app.controller.dto.NotificacaoDeletarDTO;
import br.com.unit.sistema.app.controller.dto.NotificacaoLidaDTO;
import br.com.unit.sistema.app.controller.dto.NotificacaoListagemDTO;
import br.com.unit.sistema.app.entity.Tipo;
import br.com.unit.sistema.app.services.NotificacaoService;
import jakarta.validation.Valid;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/notificacao")
public class NotificacaoController {

    @Autowired
    private NotificacaoService service;

    @GetMapping("/all/{id}")
    public ResponseEntity listarNotificacao(@PathVariable long id, Pageable paginacao){
       return service.coletarNotificacao(id, paginacao);
    }

    @GetMapping("/openNot/{id}")
    public ResponseEntity mostrarNotificacaoEspecifica(@PathVariable long id){
        return service.exibirNotificacaoEspecifica(id);
    }

    @GetMapping("/newNot/User/{id}")
    public ResponseEntity exibirNotificacoesNovas(@PathVariable long id, Pageable paginacao){
        return service.exibirNotificacaoNLida(id, paginacao);
    }

    @GetMapping("/reciveByUser/{id}")
    public ResponseEntity listarNotificacaoUsuario(@PathVariable long id, Pageable paginacao){
       return service.coletarNotificacaoUsuario(id, paginacao);
    }

    @GetMapping("/sendByUser/{id}")
    public ResponseEntity listarNotificacaoEnviada(@PathVariable long id, Pageable paginacao){
       return service.coletarNotificacaoEnviadas(id, paginacao);
    }

    @GetMapping("/filterTipo/{tipo}")
    public Page<NotificacaoListagemDTO> filtrarNotificacao(@PathVariable Tipo tipo, Pageable paginacao){
       return service.filtrarNotificacaoTipo(tipo, paginacao);
    }

    @PostMapping
    public ResponseEntity<NotificacaoDTO> criarNotificacao(@RequestBody @Valid NotificacaoDTO dados){ 
        return service.salvarNotificacao(dados); 
    }

    @PutMapping
    public ResponseEntity<NotificacaoLidaDTO> marcarComoLida(@RequestBody @Valid NotificacaoLidaDTO dados){
        return service.atualizarNotificacao(dados);
    }

    @PutMapping("/tag")
    public ResponseEntity atualizarTagNotificacao(@RequestBody @Valid NotificacaoLidaDTO dados){
        return service.atualizarTag(dados);
    }
    
    @DeleteMapping
    public ResponseEntity deletarNotificacao(@RequestBody NotificacaoDeletarDTO dados){
        return service.apagarNotificacao(dados);
    }
}
