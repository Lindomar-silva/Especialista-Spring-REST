package com.algaworks.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.model.input.GrupoInput;
import com.algaworks.algafood.domain.model.Grupo;

@Component
public class GrupoInputDisassembler {

	@Autowired
	private ModelMapper mapper;

	public Grupo toDomainModel(GrupoInput grupoInput) {
		return mapper.map(grupoInput, Grupo.class);
	}

	public void copyDomainModel(GrupoInput grupoInput, Grupo grupo) {
		mapper.map(grupoInput, grupo);
	}
}
