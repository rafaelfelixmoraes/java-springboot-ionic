package com.rafaelfelix.cursospring.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rafaelfelix.cursospring.domain.Cidade;
import com.rafaelfelix.cursospring.domain.Estado;
import com.rafaelfelix.cursospring.dto.CidadeDTO;
import com.rafaelfelix.cursospring.dto.EstadoDTO;
import com.rafaelfelix.cursospring.services.CidadeService;
import com.rafaelfelix.cursospring.services.EstadoService;

@RestController
@RequestMapping(value = "/estados")
public class EstadoResource {
	
	@Autowired
	private EstadoService service;
	
	@Autowired
	private CidadeService cidadeService;
	
	@GetMapping("/{id}")
	public ResponseEntity<Estado> findById(@PathVariable Integer id) {
		Estado obj = service.find(id);
		return ResponseEntity.ok(obj);
	}
	
	@GetMapping("")
	public ResponseEntity<List<EstadoDTO>> findAll() {
		List<Estado> listObj = service.findAll();
		List<EstadoDTO> listDto = listObj.stream().map(obj -> new EstadoDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok(listDto);
	}
	
	@GetMapping("/{estado_id}/cidades")
	public ResponseEntity<List<CidadeDTO>> findCidades(@PathVariable Integer estado_id) {
		List<Cidade> listObj = cidadeService.findByEstadoId(estado_id);
		List<CidadeDTO> listDto = listObj.stream().map(obj -> new CidadeDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok(listDto);
	}

}
