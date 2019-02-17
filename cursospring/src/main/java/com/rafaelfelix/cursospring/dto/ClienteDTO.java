package com.rafaelfelix.cursospring.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.rafaelfelix.cursospring.domain.Cliente;

public class ClienteDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	@NotEmpty(message = "Preechimento Obrigatório")
	@Size(min = 3, max = 120, message = "Campo deve possuir entre 3 e 120 caracteres")
	private String nome;

	@NotEmpty(message = "Preechimento Obrigatório")
	@Email(message = "Email inválido")
	private String email;

	public ClienteDTO() {

	}
	
	public ClienteDTO(Cliente obj) {
		this.id = obj.getId();
		this.nome = obj.getNome();
		this.email = obj.getEmail();
	}

	public ClienteDTO(Integer id, String nome, String email) {
		this.id = id;
		this.nome = nome;
		this.email = email;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

}
