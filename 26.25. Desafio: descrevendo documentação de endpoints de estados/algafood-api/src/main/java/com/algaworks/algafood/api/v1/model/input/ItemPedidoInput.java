package com.algaworks.algafood.api.v1.model.input;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemPedidoInput {

	@Valid
	@NotNull
	@Schema(example = "1")
	private Long produtoId;

	@NotNull
	@PositiveOrZero
	@Schema(example = "3")
	private int quantidade;
	
	@Schema(example = "Com adicional de cebola")
	private String observacao;
}
