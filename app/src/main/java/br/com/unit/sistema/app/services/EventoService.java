package br.com.unit.sistema.app.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.unit.sistema.app.controller.dto.NotificacaoDTO;
import br.com.unit.sistema.app.entity.Evento;
import br.com.unit.sistema.app.entity.Tipo;
import br.com.unit.sistema.app.repository.EventoRepository;


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

    public void deletar(Long id) {
        repository.deleteById(id);
    }
}
