package com.algaworks.algafood.api.openapi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.dto.VendaDiaria;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "Estatísticas")
public interface EstatisticasControllerOpenApi {

	@ApiOperation("Consulta estatísticas de vendas diárias")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "restauranteId", value = "ID do restaurante", example = "1", dataTypeClass = Integer.class),
		@ApiImplicitParam(name = "dataCriacaoInicio", value = "Data/hora da criação do pedido", example = "2019-10-30T00:00:00Z", dataTypeClass = String.class),
		@ApiImplicitParam(name = "dataCriacaoFim", value = "Data/hora final da criação do pedido", example = "2019-11-04T23:59:59Z", dataTypeClass = String.class)
	})
	List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro,
			@ApiParam(value = "Deslocamento de horário a ser considerado na consulta em relação ao UTC", defaultValue = "+00:00") String timeOffset);

	ResponseEntity<byte[]> consultarVendasDiariasPdf(VendaDiariaFilter filtro, String timeOffset);
}
