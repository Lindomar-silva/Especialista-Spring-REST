package com.algaworks.algafood.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.algaworks.algafood.api.model.EnderecoDTO;
import com.algaworks.algafood.domain.model.Endereco;

@Configuration
public class ModelMapperConfig {

	@Bean
	public ModelMapper modelMapper() {
		var modelMapper = new ModelMapper();

//		modelMapper.createTypeMap(Restaurante.class, RestauranteDTO.class)
//				.addMapping(Restaurante::getTaxaFrete, RestauranteDTO::setPrecoFrete);
		
		modelMapper.createTypeMap(Endereco.class, EnderecoDTO.class)
			.<String>addMapping(enderecoSrc -> enderecoSrc.getCidade().getEstado().getNome(),
					(enderecoDTO, value)-> enderecoDTO.getCidade().setEstado(value));

		return modelMapper;
	}
}
