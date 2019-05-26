package com.rafaelfelix.cursospring.dto;

import java.io.Serializable;
import java.util.Objects;

import com.rafaelfelix.cursospring.domain.Cidade;
import com.rafaelfelix.cursospring.domain.Estado;

public class CidadeDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String nome;
	private Estado estado;
	
	public CidadeDTO() {
		
	}
	
	public CidadeDTO(Cidade obj) {
		this.id = obj.getId();
		this.nome = obj.getNome();
		this.estado = obj.getEstado();
	}

	public CidadeDTO(Integer id, String nome, Estado estado) {
		this.id = id;
		this.nome = nome;
		this.estado = estado;
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
	 * @return the estado
	 */
	public Estado getEstado() {
		return estado;
	}

	/**
	 * @param estado the estado to set
	 */
	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CidadeDTO other = (CidadeDTO) obj;
		return Objects.equals(id, other.id);
	}

}
