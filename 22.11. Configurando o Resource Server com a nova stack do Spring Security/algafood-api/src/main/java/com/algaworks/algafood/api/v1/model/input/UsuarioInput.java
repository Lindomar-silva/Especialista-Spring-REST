package com.algaworks.algafood.api.v1.model.input;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioInput {

	@ApiModelProperty(example = "Roberto Carlos", required = true)
	@NotBlank
	private String nome;
	
	@ApiModelProperty(example = "beto.carlos@algafood.com", required = true)
	@NotBlank
	@Email
	private String email;	
}
