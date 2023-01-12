package com.algaworks.algafood.api.model.input;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoInput {

	@ApiModelProperty(example = "Contra filé Angus grelhado", required = true)
	@NotBlank
	private String nome;
	
	@ApiModelProperty(example = "Prato do chefe", required = true)
	@NotBlank
	private String descricao;
	
	@ApiModelProperty(example = "28.99", required = true)
	@NotNull
	@PositiveOrZero
	private BigDecimal preco;
	
	@ApiModelProperty(example = "true", required = true)
	@NotNull
	private Boolean ativo;// = Boolean.TRUE;

}
