package com.algaworks.algafood.api.v1.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import com.algaworks.algafood.api.v1.model.ProdutoDTO;
import com.algaworks.algafood.api.v1.model.input.ProdutoInput;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "security_auth")
public interface RestauranteProdutoControllerOpenApi {

	CollectionModel<ProdutoDTO> listar(Long restauranteId, Boolean incluirInativos);

	ProdutoDTO buscarPorProduto(Long restauranteId, Long produtoId);

	ProdutoDTO adicionar(Long restauranteId, ProdutoInput produtoInput);

	ProdutoDTO alterar(Long restauranteId, Long produtoId, ProdutoInput produtoInput);
}
