package com.algaworks.algafood.api.v1.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.algaworks.algafood.api.v1.model.GrupoDTO;

public interface UsuarioGrupoControllerOpenApi {

	CollectionModel<GrupoDTO> listar(Long usuarioId);

	ResponseEntity<Void> associarGrupo(Long usuarioId, Long grupoId);

	ResponseEntity<Void> desassociarGrupo(Long usuarioId, Long grupoId);
}
