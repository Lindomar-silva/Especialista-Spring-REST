package com.algaworks.algafood.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.algaworks.algafood.api.model.RestauranteDTO;
import com.algaworks.algafood.domain.model.Restaurante;

@Configuration
public class ModelMapperConfig {

	@Bean
	public ModelMapper modelMapper() {
		var modelMapper = new ModelMapper();

		modelMapper.createTypeMap(Restaurante.class, RestauranteDTO.class)
				.addMapping(Restaurante::getTaxaFrete, RestauranteDTO::setPrecoFrete);

		return modelMapper;
	}
}
