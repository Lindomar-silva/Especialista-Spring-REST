package com.algaworks.algafood.api.openapi.controller;

import java.util.List;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.GrupoDTO;
import com.algaworks.algafood.api.model.input.GrupoInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Grupos")
public interface GrupoControllerOpenApi {

	@ApiOperation("Lista os grupos")
	public List<GrupoDTO> listar();

	@ApiOperation("Busca um grupo por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "400", description = "ID do grupo inválido", 
				content = @Content(schema = @Schema(implementation = Problem.class))),
		@ApiResponse(responseCode = "404", description = "Grupo não encontrado", 
		content = @Content(schema = @Schema(implementation = Problem.class)))
	})
	public GrupoDTO buscar(@ApiParam(value = "ID do Grupo", example = "1") Long grupoId);

	@ApiOperation("Cadastra um grupo")
	@ApiResponse(responseCode = "201", description = "Grupo cadastrado")
	public GrupoDTO adicionar(@ApiParam(name = "corpo", value = "Representação de um grupo") GrupoInput grupoInput);

	@ApiOperation("Atualiza um grupo por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Grupo atualizado"),
		@ApiResponse(responseCode = "404", description = "Grupo não encontrado", 
				content = @Content(schema = @Schema(implementation = Problem.class)))
	})
	public GrupoDTO atualizar(@ApiParam(value = "ID do grupo", example = "1") Long grupoId, 
			@ApiParam(name = "corpo", value = "Representação de um grupo com novos dados") GrupoInput grupoInput);

	@ApiOperation("Remove um grupo por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "204", description = "Grupo Excluido"),
		@ApiResponse(responseCode = "404", description = "Grupo não encontrado", 
				content = @Content(schema = @Schema(implementation = Problem.class)))
	})
	public void remover(@ApiParam(value = "ID do grupo", example = "1") Long grupoId);
}
