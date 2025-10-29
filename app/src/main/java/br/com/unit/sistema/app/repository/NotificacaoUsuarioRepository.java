package br.com.unit.sistema.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.unit.sistema.app.entity.NotificacaoUsuario;
import br.com.unit.sistema.app.entity.NotificacaoUsuarioID;

public interface NotificacaoUsuarioRepository extends JpaRepository<NotificacaoUsuario,NotificacaoUsuarioID>{
    
    public boolean existsById(NotificacaoUsuarioID idNotificacaoUsuario);
}