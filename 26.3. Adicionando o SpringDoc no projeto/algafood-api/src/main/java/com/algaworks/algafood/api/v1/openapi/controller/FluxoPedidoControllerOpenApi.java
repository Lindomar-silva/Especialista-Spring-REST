package com.algaworks.algafood.api.v1.openapi.controller;

import org.springframework.http.ResponseEntity;

public interface FluxoPedidoControllerOpenApi {

	ResponseEntity<Void> confirmar(String pedidoUuId);

	ResponseEntity<Void> cancelar(String pedidoUuId);

	ResponseEntity<Void> entregar(String pedidoUuId);
}
