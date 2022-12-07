package com.algaworks.algafood.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeResumoDTO {

	private Long id;
	private String nome;
	
//	@JsonProperty("estado")
	private String estado;
}
