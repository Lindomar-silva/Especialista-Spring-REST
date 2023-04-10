package com.algaworks.algafood.api.v1.openapi.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.v1.model.CozinhaDTO;
import com.algaworks.algafood.api.v1.model.input.CozinhaInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Cozinhas")
public interface CozinhaControllerOpenApi {

	@ApiOperation("Lista as cozinhas")
	PagedModel<CozinhaDTO> listar(Pageable pageable); 

	@ApiOperation("Busca uma cozinha por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "400", description = "ID da cozinha inválido", 
				content = @Content(schema = @Schema(implementation = Problem.class))),
		@ApiResponse(responseCode = "404", description = "Cozinha não encontrada", 
				content = @Content(schema = @Schema(implementation = Problem.class)))
	})
	CozinhaDTO buscar(@ApiParam(value = "ID da cozinha", example = "1", required = true) Long cozinhaId);

	@ApiOperation("Cadastra uma cozinha")
	@ApiResponse(responseCode = "201", description = "cozinha cadastrada")
	CozinhaDTO adicionar(@ApiParam(name = "corpo", value = "Representação de uma cozinha", required = true) CozinhaInput cozinhaInput);

	@ApiOperation("Atualiza uma cozinha")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Cozinha atualizada"),
		@ApiResponse(responseCode = "404", description = "Cozinha não encontrada", 
				content = @Content(schema = @Schema(implementation = Problem.class)))
	})
	CozinhaDTO atualizar(@ApiParam(value = "ID da cozinha", example = "1", required = true) Long cozinhaId, 
			@ApiParam(name = "corpo", value = "Representação de uma cozinha", required = true) CozinhaInput cozinhaInput);
	
	@ApiOperation("Exclui uma cozinha por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "204", description = "Cidade Excluida"),
		@ApiResponse(responseCode = "404", description = "Cidade não encontrada", 
				content = @Content(schema = @Schema(implementation = Problem.class)))
	})
	void remover(@ApiParam(value = "ID da cozinha", required = true) Long cozinhaId);
}
