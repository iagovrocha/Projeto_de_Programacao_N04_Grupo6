package br.com.unit.sistema.app.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import br.com.unit.sistema.app.entity.NotificacaoUsuario;
import br.com.unit.sistema.app.entity.NotificacaoUsuarioID;

public interface NotificacaoUsuarioRepository extends JpaRepository<NotificacaoUsuario,NotificacaoUsuarioID>{
    
    public boolean existsById(NotificacaoUsuarioID idNotificacaoUsuario);

    @Query("SELECT nu FROM NotificacaoUsuario nu WHERE nu.id.idUser = :idUser")
    public Page<NotificacaoUsuario> findAllByIdUser(@Param("idUser") Long idUser, Pageable paginacao);

    @Transactional
    @Modifying
    @Query("DELETE FROM NotificacaoUsuario nu WHERE nu.id.idNotificacao = :idNotificacao")
    public void deleteByIdNotificacao(@Param("idNotificacao") Long idNotificacao);
}
