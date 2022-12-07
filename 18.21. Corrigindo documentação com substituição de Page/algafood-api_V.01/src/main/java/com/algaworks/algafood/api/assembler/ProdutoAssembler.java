package com.algaworks.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.ProdutoDTO;
import com.algaworks.algafood.domain.model.Produto;

@Component
public class ProdutoAssembler {

	@Autowired
	private ModelMapper mapper;

	public ProdutoDTO toDTO(Produto produto) {
		return mapper.map(produto, ProdutoDTO.class);
	}
	
	public List<ProdutoDTO> toCollectionDTO(List<Produto> produtos){
		return produtos.stream()
				.map(produto -> toDTO(produto))
				.collect(Collectors.toList());
	}
}
