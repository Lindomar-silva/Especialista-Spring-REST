package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.input.EstadoInput;
import com.algaworks.algafood.domain.model.Estado;

@Component
public class EstadoInputDisassembler {

	@Autowired
	private ModelMapper mapper;

	public Estado toDomainModel(EstadoInput estadoInput) {
		return mapper.map(estadoInput, Estado.class);
	}
	
	public void copyToDomainModel(EstadoInput estadoInput, Estado estado) {
		mapper.map(estadoInput, estado);
	}
}
