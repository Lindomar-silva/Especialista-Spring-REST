package com.algaworks.algafood.domain.service;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.StatusPedido;

@Service
public class FluxoPedidoService {

	@Autowired
	private PedidoService pedidoService;

	@Transactional
	public void confirmar(Long pedidoId) {
		Pedido pedido = pedidoService.findByIdOrNotFound(pedidoId);

		StatusPedido status = StatusPedido.CONFIRMADO;

		if (!pedido.getStatus().equals(status) && !pedido.getStatus().equals(StatusPedido.CRIADO)) {
			throw new NegocioException(String.format("Stauts do pedido '%d' não pode ser alterado de '%s' para '%s'",
					pedido.getId(), pedido.getStatus().getDescricao(), status.getDescricao()));
		}

		pedido.setStatus(status);
		pedido.setDataConfirmacao(OffsetDateTime.now());
	}
}
