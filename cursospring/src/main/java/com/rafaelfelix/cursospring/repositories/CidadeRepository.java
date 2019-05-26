package com.rafaelfelix.cursospring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.rafaelfelix.cursospring.domain.Cidade;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Integer>{

	@Transactional(readOnly = true)
	List<Cidade> findByEstadoIdOrderByNomeAsc(Integer estadoId);
}
