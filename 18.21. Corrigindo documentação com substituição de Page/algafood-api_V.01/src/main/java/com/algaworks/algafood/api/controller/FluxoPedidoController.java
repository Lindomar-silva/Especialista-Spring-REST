package com.algaworks.algafood.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.service.FluxoPedidoService;

@RestController
@RequestMapping("/pedidos/{pedidoUuId}")
public class FluxoPedidoController {

	@Autowired
	private FluxoPedidoService fluxoPedido;

	@PutMapping("/confirmacao")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void confirmar(@PathVariable String pedidoUuId) {
		fluxoPedido.confirmar(pedidoUuId);
	}
	
	@PutMapping("/cancelamento")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void cancelar(@PathVariable String pedidoUuId) {
		fluxoPedido.cancelar(pedidoUuId);
	}
	
	@PutMapping("/entregue")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void entregar(@PathVariable String pedidoUuId) {
		fluxoPedido.entregar(pedidoUuId);
	}
}
