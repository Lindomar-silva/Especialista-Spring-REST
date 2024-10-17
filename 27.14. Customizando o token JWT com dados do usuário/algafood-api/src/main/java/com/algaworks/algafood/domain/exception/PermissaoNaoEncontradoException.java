package com.algaworks.algafood.domain.exception;

public class PermissaoNaoEncontradoException extends EntidadeNaoEncontradaException {
	private static final long serialVersionUID = 1L;

	public PermissaoNaoEncontradoException(String msg) {
		super(msg);
	}

	public PermissaoNaoEncontradoException(Long produtoId) {
		this(String.format("Não existe um cadastro de Permissão com o código %d", produtoId));
	}
}
