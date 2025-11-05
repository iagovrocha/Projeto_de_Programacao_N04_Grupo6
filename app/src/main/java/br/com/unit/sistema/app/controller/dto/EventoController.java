package br.com.unit.sistema.app.controller.dto;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.unit.sistema.app.entity.Evento;
import br.com.unit.sistema.app.services.EventoService;

@RestController
@RequestMapping("/eventos")
@CrossOrigin(origins = "*") // permite chamadas de qualquer origem (útil pro front)
public class EventoController {

    @Autowired
    private EventoService eventoService;

    @GetMapping
    public List<Evento> listarTodos() {
        return eventoService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Evento> buscarPorId(@PathVariable Long id) {
        Optional<Evento> evento = eventoService.buscarPorId(id);
        return evento.map(ResponseEntity::ok)
                     .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Evento> criar(@RequestBody Evento evento) {
        Evento novoEvento = eventoService.salvar(evento);
        return ResponseEntity.ok(novoEvento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Evento> atualizar(@PathVariable Long id, @RequestBody Evento evento) {
        if (eventoService.buscarPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        evento.setId(id);
        Evento atualizado = eventoService.salvar(evento);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (eventoService.buscarPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        eventoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
