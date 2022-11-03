package com.algaworks.algafood.client.model;

import lombok.Data;

@Data
public class EnderecoDTO {

	private String cep;
	private String logradouro;
	private String numero;
	private String complemento;
	private String bairro;
	private CidadeResumoDTO cidade;
}
