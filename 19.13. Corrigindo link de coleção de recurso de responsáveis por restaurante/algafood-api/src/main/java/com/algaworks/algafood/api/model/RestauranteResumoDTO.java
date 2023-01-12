package com.algaworks.algafood.api.model;

import com.algaworks.algafood.api.model.view.RestauranteView;
import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteResumoDTO {

	@ApiModelProperty(example = "1")
	@JsonView({ RestauranteView.Resumo.class, RestauranteView.SomenteNome.class })
	private Long id;
	
	@ApiModelProperty(example = "Sabor do Paraná")
	@JsonView({ RestauranteView.Resumo.class, RestauranteView.SomenteNome.class })
	private String nome;
}
