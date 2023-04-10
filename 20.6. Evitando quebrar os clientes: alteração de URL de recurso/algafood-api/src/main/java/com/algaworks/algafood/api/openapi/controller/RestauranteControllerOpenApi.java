package com.algaworks.algafood.api.openapi.controller;

import java.util.List;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.RestauranteApenasNomeDTO;
import com.algaworks.algafood.api.model.RestauranteBasicoDTO;
import com.algaworks.algafood.api.model.RestauranteDTO;
import com.algaworks.algafood.api.model.input.RestauranteInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "Restaurantes")
public interface RestauranteControllerOpenApi {

	@ApiOperation(value = "Lista os restaurantes")
	@ApiImplicitParam(value = "Nome da projeção de pedidos", name = "projecao",	
			allowableValues = "resumo", paramType = "query", dataTypeClass = String.class)
	CollectionModel<RestauranteBasicoDTO> listar();
	
	@ApiIgnore
	@ApiOperation(value = "Lista restaurantes", hidden = true)
	CollectionModel<RestauranteApenasNomeDTO> listarResumo();
	
	@ApiOperation("Busca um restaurante por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "400", description = "ID do restaurante inválido", 
				content = @Content(schema = @Schema(implementation = Problem.class))),
		@ApiResponse(responseCode = "404", description = "Restaurante não encontrado", 
				content = @Content(schema = @Schema(implementation = Problem.class)))
	})
	RestauranteDTO buscar(@ApiParam(value = "ID do restaurante", example = "1", required = true) Long restauranteId);
	
	@ApiOperation("Cadastra um restaurante")
	@ApiResponse(responseCode = "201", description = "Restaurante cadastrado")
	RestauranteDTO adicionar(@ApiParam(name = "corpo", value = "Representação de um restaurante", 
		required = true) RestauranteInput restauranteInput);

	@ApiOperation("Atualiza um restaurante por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Restaurante atualizado"),
		@ApiResponse(responseCode = "404", description = "Restaurtante não encontrado", 
				content = @Content(schema = @Schema(implementation = Problem.class)))
	})
	RestauranteDTO atualizar(
			@ApiParam(value = "ID do restaurante", example = "1", required = true) Long restauranteId, 
			@ApiParam(name = "corpo", value = "Representação de um restaurante", required = true) RestauranteInput restauranteInput);
	
	
	@ApiOperation("Ativar um restaurante por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Restaurante ativado com sucesso"),
		@ApiResponse(responseCode = "404", description = "Restaurtante não encontrado", 
				content = @Content(schema = @Schema(implementation = Problem.class)))
	})
	ResponseEntity<Void> ativar(@ApiParam(value = "ID do restaurante", example = "1", required = true) Long restauranteId);
	
	@ApiOperation("Inativar um restaurante por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Restaurante inativado com sucesso"),
		@ApiResponse(responseCode = "404", description = "Restaurtante não encontrado", 
				content = @Content(schema = @Schema(implementation = Problem.class)))
	})
	ResponseEntity<Void> inativar(@ApiParam(value = "ID do restaurante", example = "1", required = true) Long restauranteId);
	
	@ApiOperation("Ativa múltiplos restaurantes")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Restaurantes ativados com sucesso")
    })
	void ativarMultiplos(@ApiParam(name = "corpo", value = "IDs de restaurantes", required = true) List<Long> restauranteIds);

	@ApiOperation("Inativa múltiplos restaurantes")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Restaurantes inativados com sucesso")
    })
	void inativarMultiplos(@ApiParam(name = "corpo", value = "IDs de restaurantes", required = true) List<Long> restauranteIds);
	
	@ApiOperation("Fecha um restaurante por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Restaurante fechado com sucesso"),
		@ApiResponse(responseCode = "404", description = "Restaurtante não encontrado", 
				content = @Content(schema = @Schema(implementation = Problem.class)))
	})
	ResponseEntity<Void> fechado(@ApiParam(value = "ID do restaurante", example = "1", required = true) Long restauranteId);

	@ApiOperation("Abre um restaurante por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Restaurante aberto com sucesso"),
		@ApiResponse(responseCode = "404", description = "Restaurtante não encontrado", 
				content = @Content(schema = @Schema(implementation = Problem.class)))
	})
	ResponseEntity<Void> aberto(@ApiParam(value = "ID do restaurante", example = "1", required = true) Long restauranteId);
}
