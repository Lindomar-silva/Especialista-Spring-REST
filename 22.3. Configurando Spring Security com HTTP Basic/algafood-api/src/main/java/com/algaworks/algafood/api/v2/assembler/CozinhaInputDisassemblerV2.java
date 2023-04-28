package com.algaworks.algafood.api.v2.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v2.model.input.CozinhaInputV2;
import com.algaworks.algafood.domain.model.Cozinha;

@Component
public class CozinhaInputDisassemblerV2 {

	@Autowired
	private ModelMapper mapper;
	
	public Cozinha toDomainModel(CozinhaInputV2 cozinhaInput) {
		return mapper.map(cozinhaInput, Cozinha.class);
	}
	
	public void copyToDomainModel(CozinhaInputV2 cozinhaInput, Cozinha cozinha) {
		mapper.map(cozinhaInput, cozinha);
	}
}
