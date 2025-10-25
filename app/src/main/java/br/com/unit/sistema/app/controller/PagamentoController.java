package br.com.unit.sistema.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.unit.sistema.app.controller.dto.PagamentoDTO;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {
    
    @PostMapping
    public ResponseEntity<PagamentoDTO> criarPagamento(@RequestBody PagamentoDTO pagamento) {
        System.out.println(pagamento.toString());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
