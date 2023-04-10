package com.algaworks.algafood.domain.exception;

public class UsuarioNaoEncontradoException extends EntidadeNaoEncontradaException {
	private static final long serialVersionUID = 1L;

	public UsuarioNaoEncontradoException(String msg) {
		super(msg);
	}

	public UsuarioNaoEncontradoException(Long usuarioId) {
		this(String.format("Não existe um cadastro de Usuário com o código %d", usuarioId));
	}
}
