package com.algaworks.algafood.api.v1.openapi.model;

import java.math.BigDecimal;

import com.algaworks.algafood.api.v1.model.CozinhaDTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel("RestauranteBasicoDTO")
@Getter
@Setter
public class RestauranteBasicoDTOOpenApi {

	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Thai Gourmet")
	private String nome;
	
	@ApiModelProperty(example = "12.30")
	private BigDecimal taxaFrete;
	
	private CozinhaDTO cozinha;
	

}
