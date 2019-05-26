package com.rafaelfelix.cursospring.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rafaelfelix.cursospring.domain.Estado;
import com.rafaelfelix.cursospring.repositories.EstadoRepository;
import com.rafaelfelix.cursospring.services.exceptions.ObjectNotFoundException;

@Service
public class EstadoService {
	
	@Autowired
	private EstadoRepository repo;
	
	public Estado find(Integer id) {
		Optional<Estado> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Estado.class.getName()));
	}
	
	public List<Estado> findAll() {
		List<Estado> listObj = repo.findAllByOrderByNome();
		if(listObj == null || listObj.isEmpty()) {
			throw new ObjectNotFoundException("Nenhum objeto encontrado!");
		}
		
		return listObj;
	}
}
