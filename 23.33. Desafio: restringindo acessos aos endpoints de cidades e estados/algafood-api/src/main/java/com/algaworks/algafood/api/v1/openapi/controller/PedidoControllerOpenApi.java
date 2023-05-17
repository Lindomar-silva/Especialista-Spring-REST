package com.algaworks.algafood.api.v1.openapi.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.v1.model.PedidoDTO;
import com.algaworks.algafood.api.v1.model.PedidoResumoDTO;
import com.algaworks.algafood.api.v1.model.input.PedidoInput;
import com.algaworks.algafood.domain.filter.PedidoFilter;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Pedidos")
public interface PedidoControllerOpenApi {

	@ApiImplicitParam(value = "Nomes das propriedades para filtrar na resposta, separados por vírgula", 
			name = "campos", paramType = "query", dataTypeClass = String.class)
	@ApiOperation(value = "Pesquisa os pedidos")
	PagedModel<PedidoResumoDTO> pesquisar(PedidoFilter filtro, Pageable pageable);
	
	@ApiImplicitParam(value = "Nomes das propriedades para filtrar na resposta, separados por vírgula", 
			name = "campos", paramType = "query",  dataTypeClass = String.class)
	@ApiOperation("Busca um pedido por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "404", description = "Pedido não encontrado", 
				content = @Content(schema = @Schema(implementation = Problem.class)))
	})
	PedidoDTO buscar(@ApiParam(value = "ID do pedido", 
		example = "c08f9902-47fe-4798-a2ee-41f2cbf44edd", required = true) String pedidoUuId);

	@ApiOperation("Registra um pedido")
	@ApiResponse(responseCode = "201", description = "Pedido registrado")
	PedidoDTO emitir(@ApiParam(name = "corpo", value = "Representação de um pedido", required = true) PedidoInput pedidoInput);

}
