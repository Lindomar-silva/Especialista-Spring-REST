package com.algaworks.algafood.api.model;

import com.algaworks.algafood.api.model.view.RestauranteView;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteResumoDTO {

	@JsonView({ RestauranteView.Resumo.class, RestauranteView.SomenteNome.class })
	private Long id;
	
	@JsonView({ RestauranteView.Resumo.class, RestauranteView.SomenteNome.class })
	private String nome;
}
