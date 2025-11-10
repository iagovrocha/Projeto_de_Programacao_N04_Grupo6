package br.com.unit.sistema.app.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.unit.sistema.app.entity.Evento;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {
    Page<Evento> findByIdUser(Long idUser, Pageable pageable);
    
    @Query("SELECT e FROM Evento e JOIN e.usuarios u WHERE u.id = :idUsuario")
    List<Evento> findEventosByUsuarioId(@Param("idUsuario") Long idUsuario);
}
