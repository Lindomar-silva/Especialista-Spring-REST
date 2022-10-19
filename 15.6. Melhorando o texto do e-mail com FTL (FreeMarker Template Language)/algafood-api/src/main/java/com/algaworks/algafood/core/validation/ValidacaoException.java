package com.algaworks.algafood.core.validation;

import org.springframework.validation.BindingResult;

import lombok.Getter;

@Getter
public class ValidacaoException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private BindingResult bindingResult;

	public ValidacaoException() {
	}

	public ValidacaoException(BindingResult bindingResult) {
		this.bindingResult = bindingResult;
	}

}
