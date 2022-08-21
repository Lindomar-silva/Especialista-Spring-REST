package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.input.RestauranteInput;
import com.algaworks.algafood.domain.model.Restaurante;

@Component
public class RestauranteInputDisassembler {

	@Autowired
	private ModelMapper mapper;

	public Restaurante toDomianModel(RestauranteInput restauranteInput) {
		return mapper.map(restauranteInput, Restaurante.class);
	}
}
