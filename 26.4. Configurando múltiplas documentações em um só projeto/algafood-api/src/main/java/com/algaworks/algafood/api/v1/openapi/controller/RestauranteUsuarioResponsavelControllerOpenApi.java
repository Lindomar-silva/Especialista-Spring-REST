package com.algaworks.algafood.api.v1.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.algaworks.algafood.api.v1.model.UsuarioDTO;

public interface RestauranteUsuarioResponsavelControllerOpenApi {

	CollectionModel<UsuarioDTO> listar(Long restauranteId);

	ResponseEntity<Void> associar(Long restauranteId, Long responsavelId);

	ResponseEntity<Void> desassociar(Long restauranteId, Long responsavelId);
}
