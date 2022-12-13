package com.algaworks.algafood.api.openapi.controller;

import java.util.List;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.UsuarioDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Restaurantes")
public interface RestauranteUsuarioResponsavelControllerOpenApi {
	
	@ApiOperation("Lista os usuários responsáveis associados a restaurante")
	@ApiResponses({
        @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", 
        		content = @Content(schema = @Schema(implementation = Problem.class)))
    })
	List<UsuarioDTO> listar(@ApiParam(value = "ID do restaurante", example = "1", required = true) Long restauranteId);
	
	@ApiOperation("Associação de restaurante com usuário responsável")
	@ApiResponses({
		@ApiResponse(responseCode = "204", description = "Usuário responsável associado"),
		@ApiResponse(responseCode = "404", description = "Restaurante ou usuário não encontrado", 
				content = @Content(schema = @Schema(implementation = Problem.class)))
	})
	void associar(@ApiParam(value = "ID do restaurante", example = "1", required = true) Long restauranteId, 
			@ApiParam(value = "ID do usuário", example = "3", required = true) Long responsavelId);
	
	@ApiOperation("Desassociação de restaurante com usuário responsável")
	@ApiResponses({
		@ApiResponse(responseCode = "204", description = "Usuário responsável desassociado"),
		@ApiResponse(responseCode = "404", description = "Restaurante ou usuário não encontrado", 
				content = @Content(schema = @Schema(implementation = Problem.class)))
	})
	void desassociar(@ApiParam(value = "ID do restaurante", example = "1", required = true) Long restauranteId, 
			@ApiParam(value = "ID do usuário", example = "3", required = true) Long responsavelId);
}
