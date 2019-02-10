package com.rafaelfelix.cursospring.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.rafaelfelix.cursospring.domain.Categoria;
import com.rafaelfelix.cursospring.services.CategoriaService;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaService service;
	
	@GetMapping("/listar")
	public ResponseEntity<?> listar() {
		List<Categoria> listObj = service.findAll();
		
		return ResponseEntity.ok(listObj);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getCategoriaById(@PathVariable Integer id) {
		Categoria obj = service.find(id);
		return ResponseEntity.ok(obj);
	}
	
	@PostMapping("")
	public ResponseEntity<Void> insert(@RequestBody Categoria categoria){
		categoria = service.gravaCategoria(categoria);
		URI uri =  ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").build(categoria.getId());
		return ResponseEntity.created(uri).build();
	}
}
