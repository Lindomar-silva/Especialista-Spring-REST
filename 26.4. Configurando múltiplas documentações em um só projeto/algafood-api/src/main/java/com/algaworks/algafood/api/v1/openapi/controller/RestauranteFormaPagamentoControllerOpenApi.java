package com.algaworks.algafood.api.v1.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.algaworks.algafood.api.v1.model.FormaPagamentoDTO;

public interface RestauranteFormaPagamentoControllerOpenApi {

	CollectionModel<FormaPagamentoDTO> listar(Long restauranteId);

	ResponseEntity<Void> associar(Long restauranteId, Long formaPagamentoId);

	ResponseEntity<Void> desassociar(Long restauranteId, Long formaPagamentoId);
}
