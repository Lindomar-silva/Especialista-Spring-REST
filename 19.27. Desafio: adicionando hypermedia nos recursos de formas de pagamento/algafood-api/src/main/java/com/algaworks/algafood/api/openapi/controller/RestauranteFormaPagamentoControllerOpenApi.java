package com.algaworks.algafood.api.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.FormaPagamentoDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Restaurantes")
public interface RestauranteFormaPagamentoControllerOpenApi {

	@ApiOperation("Lista as formas de pagamento associadas a restaurante")
	@ApiResponses({
        @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", 
        		content = @Content(schema = @Schema(implementation = Problem.class)))
    })
	CollectionModel<FormaPagamentoDTO> listar(@ApiParam(value = "ID do restaurante", example = "1", required = true) Long restauranteId);
	
	@ApiOperation("Associação de restaurante com forma de pagamento")
	@ApiResponses({
		@ApiResponse(responseCode = "204", description = "Forma de pagamento associada"),
		@ApiResponse(responseCode = "404", description = "Restaurante ou forma de pagamento não encontrado", 
				content = @Content(schema = @Schema(implementation = Problem.class)))
	})
	void associar(@ApiParam(value = "ID do restaurante", example = "1", required = true) Long restauranteId, 
			@ApiParam(value = "ID da forma de pagamento", example = "3", required = true) Long formaPagamentoId);
	
	@ApiOperation("Desassociação de restaurante com forma de pagamento")
	@ApiResponses({
		@ApiResponse(responseCode = "204", description = "Forma de pagamento desassociada"),
		@ApiResponse(responseCode = "404", description = "Restaurante ou forma de pagamento não encontrado", 
				content = @Content(schema = @Schema(implementation = Problem.class)))
	})
	void desassociar(@ApiParam(value = "ID do restaurante", example = "1", required = true) Long restauranteId, 
			@ApiParam(value = "ID da forma de pagamento", example = "3", required = true) Long formaPagamentoId);
}
