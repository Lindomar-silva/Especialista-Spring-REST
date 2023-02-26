package com.algaworks.algafood.api.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

//@ApiModel(value = "Cidade", description = "Representa uma cidade")
@Relation(collectionRelation = "cidades")
@Getter
@Setter
public class CidadeDTO extends RepresentationModel<CidadeDTO> {

//	@ApiModelProperty(value = "ID da cidade", example = "1")
	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Santa Barbara d'Oeste")
	private String nome;
	
	private EstadoDTO estado;
}
