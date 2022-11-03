package com.algaworks.algafood.client.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class RestauranteDTO {

	private Long id;
	private String nome;
	private BigDecimal taxaFrete;
	private Boolean ativo;
	private Boolean aberto;

	private CozinhaResumoDTO cozinha;
	private EnderecoDTO endereco;
}
