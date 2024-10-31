package com.algaworks.algafood.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

	ACESSO_NEGADO("/acesso-negado", "Acesso negado"),
	DADOS_INVALIDOS("/dados-invalidos", "Dados inválidos"),
	ERRO_DE_SISTEMA("/erro-de-sistema", "Erro de Sistema"),
	PARAMETRO_INVALIDO("/paramentro-invalido", "Parâmetro inválido"),
	REQUISICAO_INVALIDA("/requisicao-invalida", "Requisição inválida"),
	ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso"),
	NEGOCIO_EXCEPTION("/erro-negocio", "Violação de regra de negócio"),
	TAMANHO_EXCEDIDO("/tamanho-maximo-excedido","Tamanho máximo excedido"),
	RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado", "Recurso não encontrado"),
	METODO_NAO_PERMTIDO("/metodo-nao-permitido", "Método não permitido"),
	MENSAGEM_INCOMPREENSIVEL("/mensagem-incompreensivel", "Mensagem incompreensível");
	
	private String title;
	private String uri;

	private ProblemType(String path, String title) {
		this.title = title;
		this.uri = "https://algafood.com.br" + path;
	}

}
