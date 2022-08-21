package com.algaworks.algafood.api.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteDTO {

	private Long id;
	private String nome;
	private BigDecimal frete;
	private CozinhaDTO cozinha;
	private String  nomeCozinha;
	private Long idCozinha;

}
