package com.algaworks.algafood.api.model.mixin;

import java.time.LocalDateTime;
import java.util.List;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Endereco;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.Produto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public abstract class RestauranteMixin {

	@JsonIgnore
	private LocalDateTime dataCadastro;

	@JsonIgnore
	private LocalDateTime dataAtualizacao;

	@JsonIgnoreProperties(value = "nome", allowGetters = true)
	private Cozinha cozinha;

	@JsonIgnore
	private List<Produto> produtos;

	@JsonIgnore
	private List<FormaPagamento> formaPagamentos;

	@JsonIgnore
	private Endereco endereco;
}
