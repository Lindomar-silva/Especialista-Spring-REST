package com.algaworks.algafood.domain.exception;

public class FotoProdutoNaoEncontradaException extends EntidadeNaoEncontradaException {
	private static final long serialVersionUID = 1L;

	public FotoProdutoNaoEncontradaException(String msg) {
		super(msg);
	}

	public FotoProdutoNaoEncontradaException(Long produtoId) {
		this(String.format("Não existe um cadastro de Foto do produto com o código %d", produtoId));
	}

	public FotoProdutoNaoEncontradaException(Long produtoId, Long restauranteId) {
		this(String.format("Não existe um cadastro de Foto do produto com o código %d para o restaurante de código %d",
				produtoId, restauranteId));
	}
}
