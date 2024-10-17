package com.algaworks.algafood.core.springdoc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;

@Target({ ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Parameter(
		in = ParameterIn.QUERY,
		name = "clienteId", description = "ID do cliente para filtro da pesquisa",
		example = "1", schema = @Schema(type = "integer")
)
@Parameter(
		in = ParameterIn.QUERY,
		name = "restauranteId", description = "ID do restaurante para filtro da pesquisa",
		example = "1", schema = @Schema(type = "integer")
)
@Parameter(
		in = ParameterIn.QUERY,
		name = "dataCriacaoInicio", description = "Data/hora de criação inicial para filtro da pesquisa",
		example = "2024-06-27T00:00:00Z", schema = @Schema(type = "string", format = "date-time")
)
@Parameter(
		in = ParameterIn.QUERY,
		name = "dataCriacaoFim", description = "Data/hora de criação final para filtro da pesquisa",
		example = "2024-06-27T00:00:00Z", schema = @Schema(type = "string", format = "date-time")
)
@PageableParamiter
public @interface PageableParamiterPedidoFilter {}
