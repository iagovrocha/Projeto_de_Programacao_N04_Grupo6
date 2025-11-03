package br.com.unit.sistema.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.unit.sistema.app.entity.UsuarioEntidade;

public interface UsuarioRepository extends JpaRepository<UsuarioEntidade, Long> {
}
