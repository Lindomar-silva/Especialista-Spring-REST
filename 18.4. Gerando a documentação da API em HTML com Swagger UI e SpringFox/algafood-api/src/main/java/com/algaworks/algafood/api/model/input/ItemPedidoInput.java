package com.algaworks.algafood.api.model.input;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemPedidoInput {

	@Valid
	@NotNull
	private Long produtoId;

	@NotNull
	@PositiveOrZero
	private int quantidade;
	private String observacao;
}
