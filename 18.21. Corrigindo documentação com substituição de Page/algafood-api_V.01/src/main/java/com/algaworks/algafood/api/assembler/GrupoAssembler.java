package com.algaworks.algafood.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.GrupoDTO;
import com.algaworks.algafood.domain.model.Grupo;

@Component
public class GrupoAssembler {

	@Autowired
	private ModelMapper mapper;

	public GrupoDTO toDTO(Grupo grupo) {
		return mapper.map(grupo, GrupoDTO.class);
	}
	
	public List<GrupoDTO> toCollectionDTO(Collection<Grupo> grupos){
		return grupos.stream()
				.map(grupo -> toDTO(grupo))
				.collect(Collectors.toList());
	}
}
