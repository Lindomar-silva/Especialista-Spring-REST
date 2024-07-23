package com.algaworks.algafood.api.v1.openapi.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import com.algaworks.algafood.api.v1.model.PedidoDTO;
import com.algaworks.algafood.api.v1.model.PedidoResumoDTO;
import com.algaworks.algafood.api.v1.model.input.PedidoInput;
import com.algaworks.algafood.core.springdoc.PageableParamiterPedidoFilter;
import com.algaworks.algafood.domain.filter.PedidoFilter;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@SecurityRequirement(name = "security_auth")
@Tag(name = "Pedidos", description = "Gerencia os pedidos")
public interface PedidoControllerOpenApi {

	@PageableParamiterPedidoFilter
	@Operation(summary = "Lista os pedidos com paginação")
	PagedModel<PedidoResumoDTO> pesquisar(@Parameter(hidden = true) PedidoFilter filtro, @Parameter(hidden = true) Pageable pageable);

	@Operation(summary = "Busaca um pedido por ID", responses = {
			@ApiResponse(responseCode = "200"),
			@ApiResponse(responseCode = "400", description = "ID da pedido inválido", content = @Content(schema = @Schema(ref = "Problema"))),
			@ApiResponse(responseCode = "404", description = "Pedido não encontrado", content = @Content(schema = @Schema(ref = "Problema")))
	})	
	PedidoDTO buscar(@Parameter(description = "ID de um pedido", example = "1", required = true) String pedidoUuId);

	@Operation(summary = "Registra um pedido", responses = @ApiResponse(responseCode = "201", description = "Cozinha cadastrada"))
	PedidoDTO emitir(@RequestBody(description = "Representação de um novo pedido", required = true) PedidoInput pedidoInput);

}
