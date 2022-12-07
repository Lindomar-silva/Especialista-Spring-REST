package com.algaworks.algafood.domain.exception;

public class ProdutoNaoEncontradoException extends EntidadeNaoEncontradaException {
	private static final long serialVersionUID = 1L;

	public ProdutoNaoEncontradoException(String msg) {
		super(msg);
	}

	public ProdutoNaoEncontradoException(Long produtoId) {
		this(String.format("Não existe um cadastro de Produto com o código %d", produtoId));
	}

	public ProdutoNaoEncontradoException(Long produtoId, Long restauranteId) {
		this(String.format("Não existe um cadastro de Produto com o código %d para o restaurante de código %d",
				produtoId, restauranteId));
	}
}
