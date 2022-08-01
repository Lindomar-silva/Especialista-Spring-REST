package com.algaworks.algafood.api.exceptionhandler;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Problema {

	private LocalDateTime dataHora;
	private String mensagem;

	public Problema() {
	}

	public Problema(LocalDateTime dataHora, String mensagem) {
		this.dataHora = dataHora;
		this.mensagem = mensagem;
	}

}
