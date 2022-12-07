package com.algaworks.algafood.domain.exception;

public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException {
	private static final long serialVersionUID = 1L;

	public PedidoNaoEncontradoException(String pedidoUuId) {
		super(String.format("Não existe a emissão do pedido com o código %S", pedidoUuId));
	}
}
