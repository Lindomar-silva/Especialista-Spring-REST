package com.algaworks.algafood.api.v1.model.input;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoInput {

	@Schema(example = "12345-678")
	@NotBlank
	private String cep;

	@Schema(example = "Rua da Alegria")
	@NotBlank
	private String logradouro;

	@Schema(example = "\"1533\"")
	@NotBlank
	private String numero;

	@Schema(example = "Apartamento 325")
	private String complemento;

	@Schema(example = "Centro")
	@NotBlank
	private String bairro;

	@Valid
	@NotNull
	private CidadeIdInput cidade;
}
