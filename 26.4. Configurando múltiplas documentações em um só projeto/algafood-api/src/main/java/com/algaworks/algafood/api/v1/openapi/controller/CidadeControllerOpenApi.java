package com.algaworks.algafood.api.v1.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import com.algaworks.algafood.api.v1.model.CidadeDTO;
import com.algaworks.algafood.api.v1.model.input.CidadeInput;

public interface CidadeControllerOpenApi {

	CollectionModel<CidadeDTO> listar();

	CidadeDTO buscar(Long cidadeId);

	CidadeDTO adicionar(CidadeInput cidadeInput);

	CidadeDTO atualizar(Long cidadeId, CidadeInput cidadeInput);

	void remover(Long cidadeId);
}
