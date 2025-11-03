package br.com.unit.sistema.app.controller.dto;

import java.util.List;

import br.com.unit.sistema.app.entity.Evento;
import br.com.unit.sistema.app.services.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


public class EventoController {
    
    @Autowired
    private EventoService eventoservice;

    @GetMapping
    public List<Evento> listarTodos() {
        return eventoservice.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Evento> buscarPorId(@PathVariable Long id) {
        return eventoservice.buscarPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Evento> atualizar(@PathVariable Long id, @RequestBody Evento evento) {
        if (eventoservice.buscarPorId(id).isPresent()) 
            return ResponseEntity.notFound().build();
    }
    evento.setId(id;
    Evento atualizado = eventoservice.salvar(evento);
    return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (eventoService.buscarPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
    }
    eventoservice.deletar(id);
    return ResponseEntity.noContent().build();
    }

}
