package com.algaworks.algafood.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.algaworks.algafood.api.v1.model.EnderecoDTO;
import com.algaworks.algafood.api.v1.model.input.CidadeInput;
import com.algaworks.algafood.api.v1.model.input.ItemPedidoInput;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Endereco;
import com.algaworks.algafood.domain.model.ItemPedido;

@Configuration
public class ModelMapperConfig {

	@Bean
	ModelMapper modelMapper() {
		var modelMapper = new ModelMapper();

		modelMapper.createTypeMap(CidadeInput.class, Cidade.class)
			.addMappings(mapper -> mapper.skip(Cidade::setId));
			
		modelMapper.createTypeMap(Endereco.class, EnderecoDTO.class)
			.<String>addMapping(enderecoSrc -> enderecoSrc.getCidade().getEstado().getNome(),
					(enderecoDTO, value)-> enderecoDTO.getCidade().setEstado(value));
		
		modelMapper.createTypeMap(ItemPedidoInput.class, ItemPedido.class)
			.addMappings(mapper -> mapper.skip(ItemPedido::setId));

		return modelMapper;
	}
}
