package com.rafaelfelix.cursospring.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rafaelfelix.cursospring.domain.Categoria;
import com.rafaelfelix.cursospring.repositories.CategoriaRepository;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repo;
	
	public Categoria find(Integer id) {
		Optional<Categoria> obj = repo.findById(id); 
		return obj.orElse(null);
	}
	
	public List<Categoria> findAll(){
		List<Categoria> listObj = repo.findAll();
		return listObj;
	}
}
