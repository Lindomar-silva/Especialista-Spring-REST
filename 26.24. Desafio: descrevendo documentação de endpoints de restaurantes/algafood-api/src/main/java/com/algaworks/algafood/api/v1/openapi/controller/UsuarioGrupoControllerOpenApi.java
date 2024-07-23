package com.algaworks.algafood.api.v1.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.algaworks.algafood.api.v1.model.GrupoDTO;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "security_auth")
public interface UsuarioGrupoControllerOpenApi {

	CollectionModel<GrupoDTO> listar(Long usuarioId);

	ResponseEntity<Void> associarGrupo(Long usuarioId, Long grupoId);

	ResponseEntity<Void> desassociarGrupo(Long usuarioId, Long grupoId);
}
