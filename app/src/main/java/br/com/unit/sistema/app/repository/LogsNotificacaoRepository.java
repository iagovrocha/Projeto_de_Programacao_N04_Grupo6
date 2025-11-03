package br.com.unit.sistema.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.unit.sistema.app.entity.LogsNotificacao;

public interface LogsNotificacaoRepository extends JpaRepository<LogsNotificacao, Long>{
    
}
