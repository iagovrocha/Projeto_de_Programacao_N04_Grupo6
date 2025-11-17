package br.com.unit.sistema.app.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.unit.sistema.app.controller.dto.atualizarPreferenciaDTO;
import br.com.unit.sistema.app.controller.dto.criarPreferenciaDTO;
import br.com.unit.sistema.app.controller.dto.deletarPreferenciaDTO;
import br.com.unit.sistema.app.entity.Preferencia;
import br.com.unit.sistema.app.services.PreferenciaService;

@RestController
@RequestMapping("preferencia")
public class PreferenciaController {

    @Autowired
    private PreferenciaService prefService;

    @GetMapping
    public ResponseEntity listarPreferencias(Pageable paginacao){
        return prefService.listarPref(paginacao);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity listarPreferenciaUsuario(@PathVariable long id, Pageable paginacao){
        Page<Preferencia> list = prefService.listarPrefUser(id, paginacao);
        return ResponseEntity.ok().body(list);
    }

    @PostMapping
    public ResponseEntity criarNovaPreferencia(@RequestBody criarPreferenciaDTO dados){
        return prefService.cadastrarPref(dados);
    }

    @PutMapping
    public ResponseEntity atualizarPreferencia(@RequestBody atualizarPreferenciaDTO dados){
        return prefService.atualizarPref(dados);
    }

    @DeleteMapping
    public ResponseEntity deletePreferencia(@RequestBody deletarPreferenciaDTO dados){
        return prefService.deletarPref(dados);
    }
    
}
