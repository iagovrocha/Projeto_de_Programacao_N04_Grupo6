package br.com.unit.sistema.app.services;

import java.util.List;

import br.com.unit.sistema.app.entity.Evento;
import br.com.unit.sistema.app.repository.EventoRepository;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class EventoService {
    
    @Autowired
    private EventoRepository repository;

    public List<Evento> listarTodos() {
        return repository.findAll();
    }

    public Optional<Evento> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public Evento salvar(Evento evento) {
        return repository.save(evento);
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }
}
