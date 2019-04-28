package com.rafaelfelix.cursospring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.rafaelfelix.cursospring.domain.Cliente;
import com.rafaelfelix.cursospring.repositories.ClienteRepository;
import com.rafaelfelix.cursospring.security.UserSS;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private ClienteRepository clienteRepo;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Cliente cli = clienteRepo.findByEmail(email);
		
		if(cli == null) {
			throw new UsernameNotFoundException("Usuário não encontrado: ".concat(email));
		}
		
		return new UserSS(cli.getId(), cli.getEmail(), cli.getSenha(), cli.getPerfis());
	}

}
