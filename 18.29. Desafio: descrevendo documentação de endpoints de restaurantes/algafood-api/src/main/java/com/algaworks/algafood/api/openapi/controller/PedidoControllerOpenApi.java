package com.algaworks.algafood.api.openapi.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.PedidoDTO;
import com.algaworks.algafood.api.model.PedidoResumoDTO;
import com.algaworks.algafood.api.model.input.PedidoInput;
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
			name = "campos", paramType = "query", type = "string")
	@ApiOperation(value = "Pesquisa os pedidos")
	public Page<PedidoResumoDTO> pesquisar(PedidoFilter filtro, Pageable pageable);
	
	@ApiImplicitParam(value = "Nomes das propriedades para filtrar na resposta, separados por vírgula", 
			name = "campos", paramType = "query", type = "string")
	@ApiOperation("Busca um pedido por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "404", description = "Pedido não encontrado", 
				content = @Content(schema = @Schema(implementation = Problem.class)))
	})
	public PedidoDTO buscar(@ApiParam(value = "ID do pedido", 
		example = "c08f9902-47fe-4798-a2ee-41f2cbf44edd", required = true) String pedidoUuId);

	@ApiOperation("Registra um pedido")
	@ApiResponse(responseCode = "201", description = "Pedido registrado")
	public PedidoDTO emitir(@ApiParam(name = "corpo", value = "Representação de um pedido", required = true) PedidoInput pedidoInput);

}
