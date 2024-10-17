package com.algaworks.algafood.api.v1.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.algaworks.algafood.api.v1.model.FormaPagamentoDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@SecurityRequirement(name = "security_auth")
@Tag(name = "Restaurantes")
public interface RestauranteFormaPagamentoControllerOpenApi {

	@Operation(summary = "Lista as formas de pagamento associadas a restaurante por ID", responses = {
			@ApiResponse(responseCode = "200"),
			@ApiResponse(responseCode = "400", description = "ID do restaurante inválido", content = @Content(schema = @Schema(ref = "Problema"))),
			@ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(schema = @Schema(ref = "Problema")))
	})	
	CollectionModel<FormaPagamentoDTO> listar(@Parameter(description = "ID de um restaurante", example = "1", required = true) Long restauranteId);

	@Operation(summary = "Associação de restaurante com forma de pagamento", responses = {
			@ApiResponse(responseCode = "204", description = "Associação realizada com sucesso"),
			@ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(schema = @Schema(ref = "Problema"))),
			@ApiResponse(responseCode = "404", description = "Forma de pagamento não encontrada", content = @Content(schema = @Schema(ref = "Problema")))
	})
	ResponseEntity<Void> associar(@Parameter(description = "ID de um restaurante", example = "1", required = true) Long restauranteId, 
			@Parameter(description = "ID da forma de pagamento", example = "1", required = true) Long formaPagamentoId);

	@Operation(summary = "Desassociação de restaurante com forma de pagamento", responses = {
			@ApiResponse(responseCode = "204", description = "Desassociação realizada com sucesso"),
			@ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(schema = @Schema(ref = "Problema"))),
			@ApiResponse(responseCode = "404", description = "Forma de pagamento não encontrada", content = @Content(schema = @Schema(ref = "Problema")))
	})
	ResponseEntity<Void> desassociar(@Parameter(description = "ID de um restaurante", example = "1", required = true) Long restauranteId, 
			@Parameter(description = "ID da forma de pagamento", example = "1", required = true) Long formaPagamentoId);
}
