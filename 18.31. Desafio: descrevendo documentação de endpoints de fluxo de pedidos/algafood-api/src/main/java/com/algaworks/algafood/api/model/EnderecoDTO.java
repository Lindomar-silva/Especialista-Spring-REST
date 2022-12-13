package com.algaworks.algafood.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoDTO {

	@ApiModelProperty(example = "13456-789")
	private String cep;
	
	@ApiModelProperty(example = "Rua Floriano Peixoto")
	private String logradouro;
	
	@ApiModelProperty(example = "\"2022\"")
	private String numero;
	
	@ApiModelProperty(example = "Apartamento 355")
	private String complemento;
	
	@ApiModelProperty(example = "Jardim da Alegria")
	private String bairro;
	
	private CidadeResumoDTO cidade;
}
