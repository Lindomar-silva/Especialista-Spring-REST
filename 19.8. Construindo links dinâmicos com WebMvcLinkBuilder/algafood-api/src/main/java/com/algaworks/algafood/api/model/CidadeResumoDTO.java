package com.algaworks.algafood.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeResumoDTO {

	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Santa Barbara d'Oeste")
	private String nome;
	
//	@JsonProperty("estado")
	@ApiModelProperty(example = "São Paulo")
	private String estado;
}
