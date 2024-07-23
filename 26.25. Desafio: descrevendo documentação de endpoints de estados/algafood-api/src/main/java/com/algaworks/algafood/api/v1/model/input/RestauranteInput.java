package com.algaworks.algafood.api.v1.model.input;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import com.algaworks.algafood.core.validation.ValorZeroIncluiDescricao;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@ValorZeroIncluiDescricao(valorField = "taxaFrete", descricaoField = "nome", descricaoObrigatoria = "Frete Grátis")
@Getter
@Setter
public class RestauranteInput {

	@Schema(example = "Restaurante Mineiro")
	@NotBlank
	private String nome;

	@Schema(example = "13.45")
	@NotNull
	@PositiveOrZero
	private BigDecimal taxaFrete;

	@Valid
	@NotNull
	private CozinhaIdInput cozinha;

	@Valid
	@NotNull
	private EnderecoInput endereco;
}
