package com.algaworks.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.FormaPagamentoDTO;
import com.algaworks.algafood.domain.model.FormaPagamento;

@Component
public class FormaPagamentoDTOAssembler {

	@Autowired
	private ModelMapper mapper;

	public FormaPagamentoDTO toDTO(FormaPagamento formaPagamento) {
		return mapper.map(formaPagamento, FormaPagamentoDTO.class);
	}

	public List<FormaPagamentoDTO> toCollectionDTO(List<FormaPagamento> formaPagamentos) {
		return formaPagamentos.stream()
				.map(forma -> toDTO(forma))
				.collect(Collectors.toList());
	}
}
