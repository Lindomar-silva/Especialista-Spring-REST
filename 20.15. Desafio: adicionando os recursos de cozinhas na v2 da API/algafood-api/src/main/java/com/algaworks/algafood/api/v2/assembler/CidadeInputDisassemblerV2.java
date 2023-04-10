package com.algaworks.algafood.api.v2.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v2.model.input.CidadeInputV2;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;

@Component
public class CidadeInputDisassemblerV2 {

	@Autowired
	private ModelMapper mapper;

	public Cidade toDomainModel(CidadeInputV2 cidadeInput) {
		return mapper.map(cidadeInput, Cidade.class);
	}

	public void copyToDomainModel(CidadeInputV2 cidadeInput, Cidade cidade) {
		// Para evitar org.hibernate.HibernateException: identifier of an instance of 
        // com.algaworks.algafood.domain.model.Estado was altered from 1 to 2
		cidade.setEstado(new Estado());
		mapper.map(cidadeInput, cidade);
	}
}
