package com.algaworks.algafood.api.v1.model.input;

import jakarta.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SenhaInput {

	@Schema(example = "123", type = "string")
	@NotBlank
	private String senhaAtual;
	
	@Schema(example = "456", type = "string")
	@NotBlank
	private String senhaNova;	
}
