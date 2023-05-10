package com.algaworks.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.model.input.ProdutoInput;
import com.algaworks.algafood.domain.model.Produto;

@Component
public class ProdutoDisassembler {

	@Autowired
	private ModelMapper mapper;

	public Produto toDomainModel(ProdutoInput produtoInput) {
		return mapper.map(produtoInput, Produto.class);
	}

	public void copyCollectionModel(ProdutoInput produtoInput, Produto produto) {
		mapper.map(produtoInput, produto);
	}
}
