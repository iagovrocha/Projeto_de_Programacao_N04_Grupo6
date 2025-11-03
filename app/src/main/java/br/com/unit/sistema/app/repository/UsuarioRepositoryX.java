package br.com.unit.sistema.app.repository;

import br.com.unit.sistema.app.entity.UsuarioX;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepositoryX extends JpaRepository<UsuarioX, Long> {
}
