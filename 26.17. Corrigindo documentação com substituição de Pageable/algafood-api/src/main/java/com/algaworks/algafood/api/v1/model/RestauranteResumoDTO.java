package com.algaworks.algafood.api.v1.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "restaurantes")
@Getter
@Setter
public class RestauranteResumoDTO extends RepresentationModel<RestauranteResumoDTO> {

//	@JsonView({ RestauranteView.Resumo.class, RestauranteView.SomenteNome.class })
	private Long id;

//	@JsonView({ RestauranteView.Resumo.class, RestauranteView.SomenteNome.class })
	private String nome;
}
