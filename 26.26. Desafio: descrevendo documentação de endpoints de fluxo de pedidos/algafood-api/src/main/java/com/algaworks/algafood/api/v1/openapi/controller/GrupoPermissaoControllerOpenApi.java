package com.algaworks.algafood.api.v1.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.algaworks.algafood.api.v1.model.PermissaoDTO;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "security_auth")
public interface GrupoPermissaoControllerOpenApi {

	CollectionModel<PermissaoDTO> listar(Long grupoId);

	ResponseEntity<Void> associar(Long grupoId, Long permissaoId);

	ResponseEntity<Void> desassociar(Long grupoId, Long permissaoId);
}
