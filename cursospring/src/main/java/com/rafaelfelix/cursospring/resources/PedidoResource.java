package com.rafaelfelix.cursospring.resources;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
	
	@PostMapping("")
	public ResponseEntity<Void> insert(@Valid @RequestBody Pedido pedidoObj) {
		Pedido pedido = service.insert(pedidoObj);
		URI uri =  ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").build(pedido.getId());
		return ResponseEntity.created(uri).build();
	}
	
	@GetMapping("")
	public ResponseEntity<Page<Pedido>> findPage(
			@RequestParam(value = "pageNumber", defaultValue = "0") Integer page, 
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage, 
			@RequestParam(value = "orderBy", defaultValue = "instante") String orderBy, 
			@RequestParam(value = "direction", defaultValue = "DESC") String direction) {
		Page<Pedido> listObj = service.findPage(page, linesPerPage, orderBy, direction);
		return ResponseEntity.ok(listObj);
	}
}
