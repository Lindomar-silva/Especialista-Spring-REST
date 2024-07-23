package com.algaworks.algafood.api.v1.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import com.algaworks.algafood.api.v1.model.CidadeDTO;
import com.algaworks.algafood.api.v1.model.input.CidadeInput;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@SecurityRequirement(name = "security_auth")
@Tag(name = "Cidades") //, description = "Gerencia as cidades")
public interface CidadeControllerOpenApi {

	@Operation(summary = "Lista as cidades")
	CollectionModel<CidadeDTO> listar();

	@Operation(summary = "Busaca uma cidade por ID")
	CidadeDTO buscar(Long cidadeId);

	@Operation(summary = "Cadastra uma cidade", description = "Cadastro de cidade, necessita de um estado válido")
	CidadeDTO adicionar(CidadeInput cidadeInput);

	@Operation(summary = "Atualiza uma cidade por ID")
	CidadeDTO atualizar(Long cidadeId, CidadeInput cidadeInput);

	@Operation(summary = "Exclui uma cidade por ID")
	void remover(Long cidadeId);
}
