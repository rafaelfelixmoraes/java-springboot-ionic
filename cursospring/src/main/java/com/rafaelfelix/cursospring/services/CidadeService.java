package com.rafaelfelix.cursospring.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rafaelfelix.cursospring.domain.Cidade;
import com.rafaelfelix.cursospring.repositories.CidadeRepository;
import com.rafaelfelix.cursospring.services.exceptions.ObjectNotFoundException;

@Service
public class CidadeService {

	@Autowired
	private CidadeRepository repo;
	
	public Cidade find(Integer id) {
		Optional<Cidade> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cidade.class.getName()));
	}
	
	public List<Cidade> findByEstadoId(Integer estadoId) {
		List<Cidade> listObj = repo.findByEstadoIdOrderByNomeAsc(estadoId);
		if(listObj == null || listObj.isEmpty()) {
			throw new ObjectNotFoundException("Nenhum objeto encontrado!");
		}
		
		return listObj;
	}
}
