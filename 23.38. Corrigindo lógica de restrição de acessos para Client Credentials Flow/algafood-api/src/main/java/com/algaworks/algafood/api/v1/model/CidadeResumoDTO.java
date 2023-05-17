package com.algaworks.algafood.api.v1.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "cidades")
@Getter
@Setter
public class CidadeResumoDTO extends RepresentationModel<CidadeResumoDTO> {

	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Santa Barbara d'Oeste")
	private String nome;
	
//	@JsonProperty("estado")
	@ApiModelProperty(example = "São Paulo")
	private String estado;
}
