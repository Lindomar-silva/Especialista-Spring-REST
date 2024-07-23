package com.algaworks.algafood.api.v1.model;

import java.math.BigDecimal;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "restaurantes")
@Setter
@Getter
public class RestauranteBasicoDTO extends RepresentationModel<RestauranteBasicoDTO> {

	@Schema(example = "1")
	private Long id;

	@Schema(example = "Cozinha Mineira")
	private String nome;

	@Schema(example = "13.45")
	private BigDecimal taxaFrete;

	private CozinhaDTO cozinha;
}
