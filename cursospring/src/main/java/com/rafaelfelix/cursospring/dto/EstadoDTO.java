package com.rafaelfelix.cursospring.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.rafaelfelix.cursospring.domain.Cidade;
import com.rafaelfelix.cursospring.domain.Estado;

public class EstadoDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String nome;
	private List<Cidade> cidades = new ArrayList<>();
	
	public EstadoDTO() {
		
	}
	
	public EstadoDTO(Estado obj) {
		this.id = obj.getId();
		this.nome = obj.getNome();
		this.cidades = obj.getCidades();
	}

	public EstadoDTO(Integer id, String nome, List<Cidade> cidades) {
		this.id = id;
		this.nome = nome;
		this.cidades = cidades;
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
	 * @return the cidades
	 */
	public List<Cidade> getCidades() {
		return cidades;
	}

	/**
	 * @param cidades the cidades to set
	 */
	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
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
		EstadoDTO other = (EstadoDTO) obj;
		return Objects.equals(id, other.id);
	}

}
