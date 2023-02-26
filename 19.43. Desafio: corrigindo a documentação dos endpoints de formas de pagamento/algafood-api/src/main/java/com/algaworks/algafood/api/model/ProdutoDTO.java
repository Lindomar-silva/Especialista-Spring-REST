package com.algaworks.algafood.api.model;

import java.math.BigDecimal;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "produtos")
@Getter
@Setter
public class ProdutoDTO extends RepresentationModel<ProdutoDTO> {

	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Contra filé Angus grelhado")
	private String nome;
	
	@ApiModelProperty(example = "Prato do chefe")
	private String descricao;
	
	@ApiModelProperty(example = "28.99")
	private BigDecimal preco;
	
	@ApiModelProperty(example = "true")
	private Boolean ativo;
}
