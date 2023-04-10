package com.algaworks.algafood.api.v1.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.v1.model.EstadoDTO;
import com.algaworks.algafood.api.v1.model.input.EstadoInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Estados")
public interface EstadoControllerOpenApi {

	@ApiOperation("Lista os estados")
	CollectionModel<EstadoDTO> listar();

	@ApiOperation("Busca um estado por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "400", description = "ID do estado inválido", 
				content = @Content(schema = @Schema(implementation = Problem.class))),
		@ApiResponse(responseCode = "404", description = "Estado não encontrada", 
				content = @Content(schema = @Schema(implementation = Problem.class)))
	})
	EstadoDTO buscar(@ApiParam(value = "ID do estado", example = "1", required = true) Long estadoId);

	@ApiOperation("Cadastra um estado")
	@ApiResponse(responseCode = "201", description = "Estado cadastrado")
	EstadoDTO adicionar(@ApiParam(name = "corpo", value = "Representação de um estado", required = true) EstadoInput estadoInput);

	@ApiOperation("Atualiza um estado por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Estado atualizado"),
		@ApiResponse(responseCode = "404", description = "Estado não encontrado", 
				content = @Content(schema = @Schema(implementation = Problem.class)))
	})
	EstadoDTO atualizar(@ApiParam(value = "ID do estado", example = "1", required = true) Long estadoId, 
			@ApiParam(name = "corpo", value = "Representação de um estado com novos dados", required = true) EstadoInput estadoInput);
	
	@ApiOperation("Exclui um estado por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "204", description = "Estado excluido"),
		@ApiResponse(responseCode = "404", description = "Estado não encontrado", 
				content = @Content(schema = @Schema(implementation = Problem.class)))
	})
	void remover(@ApiParam(value = "ID do estado", example = "1", required = true) Long estadoId);
}
