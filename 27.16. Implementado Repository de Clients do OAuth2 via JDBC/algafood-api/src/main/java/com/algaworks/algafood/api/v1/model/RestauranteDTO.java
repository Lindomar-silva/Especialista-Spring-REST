package com.algaworks.algafood.api.v1.model;

import java.math.BigDecimal;

import org.springframework.hateoas.server.core.Relation;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "restaurantes")
@Getter
@Setter
public class RestauranteDTO extends RestauranteResumoDTO {

//	@JsonView(RestauranteView.Resumo.class)
	@Schema(example = "13.45")
	private BigDecimal taxaFrete;

//	@JsonView(RestauranteView.Resumo.class)
	private CozinhaDTO cozinha;

	private Boolean ativo;
	private Boolean aberto;
	private EnderecoDTO endereco;

}
