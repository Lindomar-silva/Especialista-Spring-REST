package com.algaworks.algafood.api.v1.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoDTO {

	@Schema(example = "12345-678")
	private String cep;
	
	@Schema(example = "Rua da Alegria")
	private String logradouro;
	
	@Schema(example = "\"1533\"")
	private String numero;
	
	@Schema(example = "Apartamento 325")
	private String complemento;
	
	@Schema(example = "Centro")
	private String bairro;
	
	private CidadeResumoDTO cidade;
}
