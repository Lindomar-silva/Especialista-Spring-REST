package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.input.FormaPagamentoInput;
import com.algaworks.algafood.domain.model.FormaPagamento;

@Component
public class FormaPagamentoInputDisassembler {

	@Autowired
	private ModelMapper mapper;

	public FormaPagamento toDomainModel(FormaPagamentoInput formaPagamentoInput) {
		return mapper.map(formaPagamentoInput, FormaPagamento.class);
	}

	public void copyDomainModel(FormaPagamentoInput formaPagamentoInput, FormaPagamento formaPagamento) {
		mapper.map(formaPagamentoInput, formaPagamento);
	}
}
