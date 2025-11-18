package br.com.unit.sistema.app.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.unit.sistema.app.controller.dto.PagamentoCreateDTO;
import br.com.unit.sistema.app.controller.dto.PagamentosListagemDTO;
import br.com.unit.sistema.app.services.PagamentosService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {
    
    @Autowired
    PagamentosService pagamentosService;

    @PostMapping
    public ResponseEntity<PagamentoCreateDTO> criarPagamento(@RequestBody @Valid PagamentoCreateDTO dados) {
        pagamentosService.create(dados);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public Page<PagamentosListagemDTO> listAll(@PageableDefault(size = 10) Pageable paginacao) {
        return pagamentosService.listAllPagamentos(paginacao);
    }

    @GetMapping("/{idUsuario}")
    public Page<PagamentosListagemDTO> listPagamentosByUsuario(@PathVariable Long idUsuario, @PageableDefault(size = 10) Pageable paginacao) {
        return pagamentosService.listPagamentosByUsuario(idUsuario, paginacao);
    }

    
}
