package br.com.unit.sistema.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.unit.sistema.app.entity.Tags;

public interface TagsRepositorys extends JpaRepository<Tags, Long>{
	boolean existsByNomeTag(String nomeTag);
}
