package com.rafaelfelix.cursospring.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.rafaelfelix.cursospring.domain.PagamentoComBoleto;

@Service
public class BoletoService {
	
	public void preencherBoleto(PagamentoComBoleto pagto, Date instantePedido) {
		Integer dueDays = 7;
		pagto.setDataPagamento(null);
		pagto.setDataVencimento(setDueDate(dueDays, instantePedido));
	}
	
	/**
	 * Calcula uma data de vencimento a partir de um objeto {@link Date} e quantidade de dias
	 * 
	 * @param dueDays A quantidade de dias para vencimento
	 * @param instante A data a ser somada com a quanitdade de dias
	 * @return A nova data de vencimento
	 */
	private static Date setDueDate(Integer dueDays, Date instante) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(instante);
		calendar.add(Calendar.DATE, dueDays);
		return calendar.getTime();
	}

}
