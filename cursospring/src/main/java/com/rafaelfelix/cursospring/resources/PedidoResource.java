package com.rafaelfelix.cursospring.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rafaelfelix.cursospring.domain.Pedido;
import com.rafaelfelix.cursospring.services.PedidoService;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoResource {
	
	@Autowired
	private PedidoService service;
	
	@GetMapping("/listar")
	public ResponseEntity<?> listAll() {
		List<Pedido> listObj = service.findAll();
		
		return ResponseEntity.ok(listObj);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Pedido> findById(@PathVariable Integer id) {
		Pedido obj = service.find(id);
		return ResponseEntity.ok(obj);
	}
}
