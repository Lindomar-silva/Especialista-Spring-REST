package com.algaworks.algafood.client.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class RestauranteResumoDTO {

	private Long id;
	private String nome;
	private BigDecimal taxaFrete;
	private CozinhaDTO cozinha;
}
