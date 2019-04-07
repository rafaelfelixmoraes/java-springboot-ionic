package com.rafaelfelix.cursospring.services;

import org.springframework.mail.SimpleMailMessage;

import com.rafaelfelix.cursospring.domain.Pedido;

public interface EmailService {
	
	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);

}
