package br.com.unit.sistema.app.repository;

import br.com.unit.sistema.app.entity.NotificacaoUsuario;
import br.com.unit.sistema.app.entity.NotificacaoUsuarioId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificacaoUsuarioRepository extends JpaRepository<NotificacaoUsuario, NotificacaoUsuarioId> {
}
