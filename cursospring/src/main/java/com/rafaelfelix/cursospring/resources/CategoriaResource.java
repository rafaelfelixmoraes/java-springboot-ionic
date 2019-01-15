package com.rafaelfelix.cursospring.resources;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {
	
	@GetMapping("/listar")
	public String listar() {
		return new String("Olha , parece que o Rest está funcionando hein...");
	}
}
