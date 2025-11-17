package br.com.unit.sistema.app.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import br.com.unit.sistema.app.entity.Preferencia;

public interface PreferenciaRepository extends JpaRepository<Preferencia, Long>{

    @Query("SELECT pref FROM Preferencia pref WHERE pref.idUser = :idUser")
    public Page<Preferencia> findAllByIdUser(@Param("idUser") Long idUser, Pageable paginacao);
}
