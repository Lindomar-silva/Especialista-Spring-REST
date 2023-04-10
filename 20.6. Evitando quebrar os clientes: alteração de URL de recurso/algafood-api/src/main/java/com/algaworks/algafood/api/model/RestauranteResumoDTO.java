package com.algaworks.algafood.api.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "restaurantes")
@Getter
@Setter
public class RestauranteResumoDTO extends RepresentationModel<RestauranteResumoDTO> {

	@ApiModelProperty(example = "1")
//	@JsonView({ RestauranteView.Resumo.class, RestauranteView.SomenteNome.class })
	private Long id;
	
	@ApiModelProperty(example = "Sabor do Paraná")
//	@JsonView({ RestauranteView.Resumo.class, RestauranteView.SomenteNome.class })
	private String nome;
}
