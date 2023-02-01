package com.algaworks.algafood.api.openapi.model;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel("Page")
@Getter
@Setter
public class PageModelOpenApi<T> {

	private List<T> content;

	@ApiModelProperty(value = "Quantidade de registro por páginas", example = "10")
	private Long size;

	@ApiModelProperty(value = "Total de registros", example = "50")
	private Long totalElements;

	@ApiModelProperty(value = "Total de páginas", example = "5")
	private Long totalPages;

	@ApiModelProperty(value = "Número da página (começa com 0)", example = "0")
	private Long pageNumber;
}
