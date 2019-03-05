package com.rafaelfelix.cursospring.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.rafaelfelix.cursospring.domain.Categoria;
import com.rafaelfelix.cursospring.domain.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

	/**
	 * Realiza a consulta de produtos através do nome e de uma lista de categorias,
	 * utilizando o padrão de nomes do Spring Data.
	 * 
	 * @see <a href="https://docs.spring.io/spring-data/jpa/docs/current/reference/html/"> Spring Data JPA Documentation </a>
	 * 
	 * @param nome O termo a ser pesquisado
	 * @param categorias A lista de Categorias cujo o termo deve ser pesquisado
	 * @param pageRequest Os parametros de Paginação
	 * @return O resultado a busca paginado
	 */
	@Transactional(readOnly = true)
	Page<Produto> findDistinctByNomeContainingIgnoreCaseAndCategoriasIn(String nome, List<Categoria> categorias, Pageable pageRequest);

}
