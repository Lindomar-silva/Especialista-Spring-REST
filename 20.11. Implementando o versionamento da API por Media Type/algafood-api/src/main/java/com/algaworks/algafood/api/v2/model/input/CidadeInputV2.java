package com.algaworks.algafood.api.v2.model.input;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeInputV2 {

	@ApiModelProperty(example = "Santa Barbara d'Oeste", required = true)
	@NotBlank
	private String nomeCidade;

	@ApiModelProperty(example = "1", required = true)
	@NotNull
	private Long idEstado;
}
