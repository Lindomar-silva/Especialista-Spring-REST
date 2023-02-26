package com.algaworks.algafood.api.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.GrupoDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Usuários")
public interface UsuarioGrupoControllerOpenApi {

	@ApiOperation("Lista os grupos associados a um usuário")
	@ApiResponse(responseCode = "404", description = "Usuário não encontrado", 
				content = @Content(schema = @Schema(implementation = Problem.class)))
	CollectionModel<GrupoDTO> listar(@ApiParam(value = "ID do usuário", example = "1", required = true) Long usuarioId);

	@ApiOperation("Associação de grupo com usuário")
	@ApiResponses({
		@ApiResponse(responseCode = "204", description = "Associação realizada com sucesso"),
		@ApiResponse(responseCode = "404", description = "Usuártio ou grupo não encontrado", 
				content = @Content(schema = @Schema(implementation = Problem.class)))
	})
	ResponseEntity<Void> associarGrupo(@ApiParam(value = "ID do usuário", example = "1", required = true) Long usuarioId, 
			@ApiParam(value = "ID do grupo", example = "3", required = true) Long grupoId);
	
	@ApiOperation("Desassociação de grupo com usuário")
	@ApiResponses({
		@ApiResponse(responseCode = "204", description = "desassociação realizada com sucesso"),
		@ApiResponse(responseCode = "404", description = "Usuártio ou grupo não encontrado", 
				content = @Content(schema = @Schema(implementation = Problem.class)))
	})
	ResponseEntity<Void> desassociarGrupo(@ApiParam(value = "ID do usuário", example = "1", required = true) Long usuarioId, 
			@ApiParam(value = "ID do grupo", example = "3", required = true) Long grupoId);
}
