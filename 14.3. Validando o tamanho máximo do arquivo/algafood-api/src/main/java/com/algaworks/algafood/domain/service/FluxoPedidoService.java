package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.Pedido;

@Service
public class FluxoPedidoService {

	@Autowired
	private PedidoService pedidoService;

	@Transactional
	public void confirmar(String pedidoId) {
		Pedido pedido = pedidoService.findByIdOrNotFound(pedidoId);
		pedido.confirmar();
	}

	@Transactional
	public void cancelar(String pedidoId) {
		Pedido pedido = pedidoService.findByIdOrNotFound(pedidoId);
		pedido.cancelar();
	}

	@Transactional
	public void entregar(String pedidoId) {
		Pedido pedido = pedidoService.findByIdOrNotFound(pedidoId);
		pedido.entregar();
	}
}
