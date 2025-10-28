package br.com.unit.sistema.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.unit.sistema.app.entity.Notificacao;

public interface NotificacaoRepository extends JpaRepository<Notificacao, Long>{

    public boolean existsById(Long id);
    
}
