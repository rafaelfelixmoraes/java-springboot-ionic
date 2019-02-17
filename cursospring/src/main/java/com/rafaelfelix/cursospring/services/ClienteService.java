package com.rafaelfelix.cursospring.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.rafaelfelix.cursospring.domain.Cliente;
import com.rafaelfelix.cursospring.dto.ClienteDTO;
import com.rafaelfelix.cursospring.repositories.ClienteRepository;
import com.rafaelfelix.cursospring.services.exceptions.DataIntegrityException;
import com.rafaelfelix.cursospring.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;
	
	public Cliente find(Integer id) {
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
	
	public List<Cliente> findAll(){
		List<Cliente> listObj = repo.findAll();
		if(listObj == null || listObj.isEmpty()) {
			throw new ObjectNotFoundException("Nenhum objeto encontrado!");
		}
		
		return listObj;
	}
	
	public Cliente update(Cliente cliente) {
		Cliente newCliente = this.find(cliente.getId());
		updateData(newCliente, cliente);
		return repo.saveAndFlush(newCliente);
	}
	
	public void delete(Integer id) {
		this.find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Falha ao deletar objeto. Existe(m) registro(s) relacionado(s)");
		}
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Cliente fromDTO(ClienteDTO objDTO) {
		return new Cliente(objDTO.getId(), objDTO.getNome(), objDTO.getEmail(), null, null);
	}
	
	private void updateData(Cliente newCliente, Cliente oldCliente) {
		newCliente.setNome(oldCliente.getNome());
		newCliente.setEmail(oldCliente.getEmail());
	}
}
