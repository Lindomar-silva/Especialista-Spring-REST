package com.algaworks.algafood.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {
	
	PARAMETRO_INVALIDO("/paramentro-invalido", "Parâmetro inválido"),
	REQUISICAO_INVALIDA("/requisicao-invalida", "Requisição inválida"),
	ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso"),
	NEGOCIO_EXCEPTION("/erro-negocio", "Violação de regra de negócio"),
	ENTIDADE_NAO_ENCONTRADA("/entidade-nao-encontrada", "Entidade não encontrada");

	private String title;
	private String uri;

	private ProblemType(String path, String title) {
		this.title = title;
		this.uri = "https://algafood.com.br" + path;
	}

}
