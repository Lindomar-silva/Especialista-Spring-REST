package com.algaworks.algafood.domain.exception;

public class RestauranteNaoEncontradoException extends EntidadeNaoEncontradaException {
	private static final long serialVersionUID = 1L;

	public RestauranteNaoEncontradoException(String msg) {
		super(msg);
	}

	public RestauranteNaoEncontradoException(Long retauranteId) {
		this(String.format("Não existe um cadastro de Restaurante com o código %d", retauranteId));
	}
}
