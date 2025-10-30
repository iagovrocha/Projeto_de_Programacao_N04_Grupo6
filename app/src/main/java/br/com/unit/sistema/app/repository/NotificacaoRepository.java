package br.com.unit.sistema.app.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.unit.sistema.app.entity.Notificacao;
import br.com.unit.sistema.app.entity.Tipo;

public interface NotificacaoRepository extends JpaRepository<Notificacao, Long>{
    
    public Page<Notificacao> findByIdUser(long idUser, Pageable paginacao);

    public Page<Notificacao> findByTipo(Tipo tipo, Pageable paginacao);
}
