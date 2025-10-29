package br.com.unit.sistema.app.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.unit.sistema.app.entity.Pagamentos;

public interface PagamentosRepositorys extends JpaRepository<Pagamentos, Long>{
    
    Page<Pagamentos> findByIdUsuario(Long idUsuario, Pageable pageable);
    
}
