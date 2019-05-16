package com.rafaelfelix.cursospring.services;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.rafaelfelix.cursospring.domain.Cliente;
import com.rafaelfelix.cursospring.repositories.ClienteRepository;
import com.rafaelfelix.cursospring.services.exceptions.ObjectNotFoundException;

@Service
public class AuthService {
	
	@Autowired
	private ClienteRepository clienteRepo;
	
	@Autowired
	private BCryptPasswordEncoder bCrypt;
	
	@Autowired
	private EmailService emailService;
	
	public void sendNewPassword(String email) {
		Cliente cliente = clienteRepo.findByEmail(email);
		
		if(cliente == null) {
			throw new ObjectNotFoundException("E-mail não encontrado");
		}
		
		String newPass = generateNewPassword();
		cliente.setSenha(bCrypt.encode(newPass));
		
		clienteRepo.save(cliente);
		
		emailService.sendNewPasswordEmail(cliente, newPass);
	}

	private String generateNewPassword() {
		int length = 10;
		boolean useLetters = true;
		boolean useNumbers = true;
		
		String generatedString = RandomStringUtils.random(length, useLetters, useNumbers);

		return generatedString;
	}
}
