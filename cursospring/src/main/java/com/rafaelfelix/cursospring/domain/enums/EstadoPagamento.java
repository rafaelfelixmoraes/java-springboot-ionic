package com.rafaelfelix.cursospring.domain.enums;

public enum EstadoPagamento {
	PENDENTE(1, "Pedido Pendente"),
	QUITADO(2, "Pedido Quitado"),
	CANCELADO(3, "Pedido Cancelado");
	
	private int cod;
	private String descricao;
	
	private EstadoPagamento(int cod, String descricao) { 
		this.cod = cod;
		this.descricao = descricao;
	}
	
	public int getCod() {
		return cod;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public static EstadoPagamento toEnum(Integer id) {
		if (id == null) { 
			return null;
		}
		for (EstadoPagamento x : EstadoPagamento.values()) { 
			if (id.equals(x.getCod())) {
				return x; 
			}
		}
		throw new IllegalArgumentException("Id inválido " + id);
	}

}
