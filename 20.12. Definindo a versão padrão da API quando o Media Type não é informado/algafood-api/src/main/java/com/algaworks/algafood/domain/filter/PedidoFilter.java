package com.algaworks.algafood.domain.filter;

import java.time.OffsetDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoFilter {

	@ApiModelProperty(value = "ID do cliente para filtro de pesquisa", example = "1")
	private Long clienteId;
	
	@ApiModelProperty(value = "ID do restaurante para filtro de pesquisa", example = "1")
	private Long restauranteId;
	
	@ApiModelProperty(value = "Data/hora de criação inicial para filtro da pesquisa", example = "2022-11-30T00:00:00Z")
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private OffsetDateTime dataCriacaoInicio;
	
	@ApiModelProperty(value = "Data/hora de criação final para filtro da pesquisa", example = "2022-12-30T00:00:00Z")
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private OffsetDateTime dataCriacaoFim;
}
