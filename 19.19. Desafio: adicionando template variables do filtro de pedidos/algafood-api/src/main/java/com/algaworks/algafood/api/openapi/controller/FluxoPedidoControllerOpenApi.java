package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.Problem;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Pedidos")
public interface FluxoPedidoControllerOpenApi {

	@ApiOperation("Confirma um pedido por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "204", description = "Pedido confirmado"),
		@ApiResponse(responseCode = "404", description = "Pedido não encontrado", 
				content = @Content(schema = @Schema(implementation = Problem.class)))
	})
	void confirmar(@ApiParam(value = "ID do pedido", example = "c08f9902-47fe-4798-a2ee-41f2cbf44edd", required = true) String pedidoUuId);
	
	@ApiOperation("Cancela um pedido por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "204", description = "Pedido cancelado"),
		@ApiResponse(responseCode = "404", description = "Pedido não encontrado", 
				content = @Content(schema = @Schema(implementation = Problem.class)))
	})
	void cancelar(@ApiParam(value = "ID do pedido", example = "c08f9902-47fe-4798-a2ee-41f2cbf44edd", required = true) String pedidoUuId);
	
	@ApiOperation("Entrega um pedido por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "204", description = "Pedido entregue"),
		@ApiResponse(responseCode = "404", description = "Pedido não encontrado", 
				content = @Content(schema = @Schema(implementation = Problem.class)))
	})
	void entregar(@ApiParam(value = "ID do pedido", example = "c08f9902-47fe-4798-a2ee-41f2cbf44edd", required = true) String pedidoUuId);
}
