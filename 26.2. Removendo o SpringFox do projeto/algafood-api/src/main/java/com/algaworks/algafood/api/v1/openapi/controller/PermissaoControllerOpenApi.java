package com.algaworks.algafood.api.v1.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import com.algaworks.algafood.api.v1.model.PermissaoDTO;

public interface PermissaoControllerOpenApi {

	CollectionModel<PermissaoDTO> listar();
}
