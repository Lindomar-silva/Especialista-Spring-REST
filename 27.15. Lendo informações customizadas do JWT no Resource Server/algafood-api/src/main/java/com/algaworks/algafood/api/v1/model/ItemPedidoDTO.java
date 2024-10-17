package com.algaworks.algafood.api.v1.model;

import java.math.BigDecimal;

import org.springframework.hateoas.RepresentationModel;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemPedidoDTO extends RepresentationModel<ItemPedidoDTO> {

	@Schema(example = "1")
	private Long produtoId;
	
	@Schema(example = "Porco com molho agridoce")
	private String produtoNome;
	
	@Schema(example = "3")
	private Integer quantidade;
	
	@Schema(example = "78.90")
	private BigDecimal precoUnitario;
	
	@Schema(example = "236,70")
	private BigDecimal precoTotal;
	
	@Schema(example = "Com adicional de cebola")
	private String observacao;

}
