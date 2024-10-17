package com.algaworks.algafood.api.v1.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "cozinhas")
@Getter
@Setter
public class CozinhaDTO extends RepresentationModel<CozinhaDTO> {

//	@JsonView(RestauranteView.Resumo.class)
	@Schema(example = "1")
	private Long id;
	
//	@JsonView(RestauranteView.Resumo.class)
	@Schema(example = "Brasileira")
	private String nome;
}
