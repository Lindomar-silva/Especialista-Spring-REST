package com.algaworks.algafood.api.model;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemPedidoDTO {

	@ApiModelProperty(example = "1")
	private Long produtoId;
	
	@ApiModelProperty(example = "Porco com molho agridoce")
	private String produtoNome;
	
	@ApiModelProperty(example = "3")
	private Integer quantidade;
	
	@ApiModelProperty(example = "57,23")
	private BigDecimal precoUnitario;
	
	@ApiModelProperty(example = "171,69")
	private BigDecimal precoTotal;
	
	@ApiModelProperty(example = "Não adicionar cebola, por favor!")
	private String observacao;

}
