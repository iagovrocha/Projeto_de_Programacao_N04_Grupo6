package br.com.unit.sistema.app.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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

import br.com.unit.sistema.app.controller.dto.EventoCreateDTO;
import br.com.unit.sistema.app.controller.dto.EventoUpdateDTO;
import br.com.unit.sistema.app.controller.dto.InscricaoDTO;
import br.com.unit.sistema.app.controller.dto.InscricaoResponseDTO;
import br.com.unit.sistema.app.entity.Evento;
import br.com.unit.sistema.app.services.EventoService;
import br.com.unit.sistema.app.services.InscricaoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/eventos")
@CrossOrigin(origins = "*")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    @Autowired
    private InscricaoService inscricaoService;

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

    @GetMapping("/createByOrg/{id}")
    public ResponseEntity<Page<Evento>> listarEventosPorOrg(@PathVariable Long id, Pageable paginacao) {
        return eventoService.coletarEventosCriados(id, paginacao);
    }

    @GetMapping("/eventosinscritos/{id}")
    public ResponseEntity<List<Evento>> listarEventosInscritos(@PathVariable Long id) {
        List<Evento> eventos = eventoService.buscarEventosInscritosPorUsuario(id);
        return ResponseEntity.ok(eventos);
    }

    @PostMapping
    public ResponseEntity<Evento> criar(@RequestBody @Valid EventoCreateDTO eventoDTO) {
        Evento novoEvento = eventoService.criarEvento(eventoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoEvento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Evento> atualizar(@PathVariable Long id, @RequestBody @Valid EventoUpdateDTO eventoDTO) {
        try {
            Evento atualizado = eventoService.atualizarEvento(id, eventoDTO);
            return ResponseEntity.ok(atualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (eventoService.buscarPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        eventoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/inscrever")
    public ResponseEntity<?> inscreverUsuario(@RequestBody @Valid InscricaoDTO inscricaoDTO) {
        try {
            InscricaoResponseDTO response = inscricaoService.inscreverUsuarioEmEvento(inscricaoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}/inscritos")
    public ResponseEntity<List<Long>> listarIdUsuariosInscritos(@PathVariable Long id) {
        try {
            List<Long> idsUsuarios = eventoService.buscarIdsUsuariosInscritosPorEvento(id);
            return ResponseEntity.ok(idsUsuarios);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
    