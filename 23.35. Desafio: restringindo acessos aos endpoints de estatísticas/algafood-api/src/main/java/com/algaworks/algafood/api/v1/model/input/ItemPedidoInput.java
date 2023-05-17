package com.algaworks.algafood.api.v1.model.input;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemPedidoInput {

	@ApiModelProperty(example = "1", required = true)
	@Valid
	@NotNull
	private Long produtoId;

	@ApiModelProperty(example = "3", required = true)
	@NotNull
	@PositiveOrZero
	private int quantidade;
	
	@ApiModelProperty(example = "Não adicionar cebola, por favor!", required = true)
	private String observacao;
}
