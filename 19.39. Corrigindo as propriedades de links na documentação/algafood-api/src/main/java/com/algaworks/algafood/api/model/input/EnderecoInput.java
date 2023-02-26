package com.algaworks.algafood.api.model.input;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoInput {

	@ApiModelProperty(example = "13456-789", required = true)
	@NotBlank
	private String cep;

	@ApiModelProperty(example = "Rua Floriano Peixoto", required = true)
	@NotBlank
	private String logradouro;

	@ApiModelProperty(example = "\"2022\"", required = true)
	@NotBlank
	private String numero;

	@ApiModelProperty(example = "Apartamento 355")
	private String complemento;

	@ApiModelProperty(example = "Jardim da Alegria", required = true)
	@NotBlank
	private String bairro;

	@Valid
	@NotNull
	private CidadeIdInput cidade;
}
