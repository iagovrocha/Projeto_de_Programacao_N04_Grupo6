package br.com.unit.sistema.app.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.unit.sistema.app.controller.dto.EventoCreateDTO;
import br.com.unit.sistema.app.controller.dto.EventoUpdateDTO;
import br.com.unit.sistema.app.controller.dto.NotificacaoDTO;
import br.com.unit.sistema.app.entity.Evento;
import br.com.unit.sistema.app.entity.Tipo;
import br.com.unit.sistema.app.repository.EventoRepository;
import jakarta.transaction.Transactional;


@Service
public class EventoService {
    
    @Autowired
    private EventoRepository repository;

    @Autowired NotificacaoService notificacaoService;

    public List<Evento> listarTodos() {
        return repository.findAll();
    }

    public Optional<Evento> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public Evento salvar(Evento evento) {
        Evento eventoSalvo = repository.save(evento);
        notificacaoService.salvarNotificacao(new NotificacaoDTO(eventoSalvo, Tipo.AVISO));
        return eventoSalvo;
    }

    @Transactional
    public Evento criarEvento(EventoCreateDTO dto) {
        Evento evento = new Evento(dto);
        
        Evento eventoSalvo = repository.save(evento);
        notificacaoService.salvarNotificacao(new NotificacaoDTO(eventoSalvo, Tipo.AVISO));
        return eventoSalvo;
    }

    @Transactional
    public Evento atualizarEvento(Long id, EventoUpdateDTO dto) {
        Evento evento = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Evento não encontrado"));
        
        evento.atualizar(dto);
        
        Evento eventoAtualizado = repository.save(evento);
        notificacaoService.salvarNotificacao(new NotificacaoDTO(eventoAtualizado, Tipo.AVISO));
        return eventoAtualizado;
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }

    public ResponseEntity<Page<Evento>> coletarEventosCriados(Long idOrganizador, Pageable paginacao) {
        Page<Evento> eventos = repository.findByIdUser(idOrganizador, paginacao);
        return ResponseEntity.ok(eventos);
    }

    public List<Evento> buscarEventosInscritosPorUsuario(Long idUsuario) {
        return repository.findEventosByUsuarioId(idUsuario);
    }

    public List<Long> buscarIdsUsuariosInscritosPorEvento(Long idEvento) {
        Evento evento = repository.findById(idEvento)
            .orElseThrow(() -> new RuntimeException("Evento não encontrado"));
        
        return evento.getUsuarios().stream()
            .map(usuario -> usuario.getId())
            .toList();
    }
}
