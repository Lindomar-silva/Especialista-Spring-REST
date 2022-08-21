package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.input.RestauranteInput;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;

@Component
public class RestauranteInputDisassembler {

	@Autowired
	private ModelMapper mapper;

	public Restaurante toDomianModel(RestauranteInput restauranteInput) {
		return mapper.map(restauranteInput, Restaurante.class);
	}
	
	public void copyToDomainModel(RestauranteInput restauranteInput, Restaurante restaurante) {
		// Evitar: org.hibernate.HibernateException: identifier of an instance of
		// com.algaworks.algafood.domain.model.Cozinha was altered from 1 to 2
		restaurante.setCozinha(new Cozinha());
		
		mapper.map(restauranteInput, restaurante);
	}
}
