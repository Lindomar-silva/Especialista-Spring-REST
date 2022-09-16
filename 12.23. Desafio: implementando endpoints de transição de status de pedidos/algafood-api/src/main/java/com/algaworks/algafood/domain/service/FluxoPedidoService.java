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

		if (statusNotEquals(pedido, status)) {
			if (!pedido.getStatus().equals(StatusPedido.CRIADO)) {
				msgPedidoNaoPodeSerAlterado(pedido, status);
			} else {
				pedido.setStatus(status);
				pedido.setDataConfirmacao(OffsetDateTime.now());
			}
		}
	}

	@Transactional
	public void cancelar(Long pedidoId) {
		Pedido pedido = pedidoService.findByIdOrNotFound(pedidoId);

		StatusPedido status = StatusPedido.CANCELADO;

		if (statusNotEquals(pedido, status)) {
			if (pedido.getStatus().equals(StatusPedido.CRIADO)) {
				pedido.setStatus(status);
				pedido.setDataCancelamento(OffsetDateTime.now());
			} else {
				msgPedidoNaoPodeSerAlterado(pedido, status);
			}
		}
	}

	@Transactional
	public void entregar(Long pedidoId) {
		Pedido pedido = pedidoService.findByIdOrNotFound(pedidoId);

		StatusPedido status = StatusPedido.ENTREGUE;

		if (statusNotEquals(pedido, status)) {
			if (pedido.getStatus().equals(StatusPedido.CONFIRMADO)) {
				pedido.setStatus(status);
				pedido.setDataEntrega(OffsetDateTime.now());
			} else {
				msgPedidoNaoPodeSerAlterado(pedido, status);
			}
		}
	}

	private boolean statusNotEquals(Pedido pedido, StatusPedido status) {
		return !pedido.getStatus().equals(status);
	}

	private void msgPedidoNaoPodeSerAlterado(Pedido pedido, StatusPedido status) {
		throw new NegocioException(String.format("Stauts do pedido '%d' não pode ser alterado de '%s' para '%s'",
				pedido.getId(), pedido.getStatus().getDescricao(), status.getDescricao()));
	}

}
