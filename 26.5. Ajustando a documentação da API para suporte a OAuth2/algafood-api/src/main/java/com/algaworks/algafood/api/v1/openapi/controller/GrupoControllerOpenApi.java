package com.algaworks.algafood.api.v1.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import com.algaworks.algafood.api.v1.model.GrupoDTO;
import com.algaworks.algafood.api.v1.model.input.GrupoInput;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "security_auth")
public interface GrupoControllerOpenApi {

	CollectionModel<GrupoDTO> listar();

	GrupoDTO buscar(Long grupoId);

	GrupoDTO adicionar(GrupoInput grupoInput);

	GrupoDTO atualizar(Long grupoId, GrupoInput grupoInput);

	void remover(Long grupoId);
}
