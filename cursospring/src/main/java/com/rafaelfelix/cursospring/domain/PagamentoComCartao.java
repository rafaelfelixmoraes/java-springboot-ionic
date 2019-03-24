package com.rafaelfelix.cursospring.domain;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.rafaelfelix.cursospring.domain.enums.EstadoPagamento;

@Entity
@JsonTypeName("cartao")
public class PagamentoComCartao extends Pagamento {
	private static final long serialVersionUID = 3254214855231295614L;
	
	private Integer numeroDeParcelas;
	
	public PagamentoComCartao() {
		
	}

	public PagamentoComCartao(Integer id, EstadoPagamento estado, Pedido pedido, Integer numeroDeParcelas) {
		super(id, estado, pedido);
		this.numeroDeParcelas = numeroDeParcelas;
	}

	/**
	 * @return the numeroDeParcelas
	 */
	public Integer getNumeroDeParcelas() {
		return numeroDeParcelas;
	}

	/**
	 * @param numeroDeParcelas the numeroDeParcelas to set
	 */
	public void setNumeroDeParcelas(Integer numeroDeParcelas) {
		this.numeroDeParcelas = numeroDeParcelas;
	}

}
