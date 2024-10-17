package com.algaworks.algafood.api.v1.openapi.controller;

import java.util.List;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.algaworks.algafood.api.v1.model.RestauranteApenasNomeDTO;
import com.algaworks.algafood.api.v1.model.RestauranteBasicoDTO;
import com.algaworks.algafood.api.v1.model.RestauranteDTO;
import com.algaworks.algafood.api.v1.model.input.RestauranteInput;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@SecurityRequirement(name = "security_auth")
@Tag(name = "Restaurantes", description = "Gerencia restaurante")
public interface RestauranteControllerOpenApi {

	@Operation(summary = "Lista os restaurantes", parameters = {
			@Parameter(name = "projecao", description = "Nome da projeção", 
					example = "resumo", in = ParameterIn.QUERY, required = false) 
	})
	CollectionModel<RestauranteBasicoDTO> listar();

	CollectionModel<RestauranteApenasNomeDTO> listarResumo();

	@Operation(summary = "Busca um restaurante por ID", responses = {
			@ApiResponse(responseCode = "200"),
			@ApiResponse(responseCode = "400", description = "ID do restaurante inválido", content = @Content(schema = @Schema(ref = "Problema"))),
			@ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(schema = @Schema(ref = "Problema")))
	})
	RestauranteDTO buscar(@Parameter(description = "ID de um restaurante", example = "1", required = true) Long restauranteId);

	@Operation(summary = "Cadastra um restaurante", responses = @ApiResponse(responseCode = "201", description = "Restaurante cadastrado"))
	RestauranteDTO adicionar(@RequestBody(description = "Representação de um novo restaurante", required = true) RestauranteInput restauranteInput);

	@Operation(summary = "Atualiza um restaurante por ID", responses = {
			@ApiResponse(responseCode = "204", description = "Restaurante atualizado"),
			@ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(schema = @Schema(ref = "Problema")))
	})
	RestauranteDTO atualizar(@Parameter(description = "ID de um restaurante", example = "1", required = true) Long restauranteId, 
			@RequestBody(description = "Representação de um restaurante com os novos dados", required = true) RestauranteInput restauranteInput);

	@Operation(summary = "Ativa um restaurante por ID", responses = {
			@ApiResponse(responseCode = "204", description = "Restaurante ativado"),
			@ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(schema = @Schema(ref = "Problema")))
	})
	ResponseEntity<Void> ativar(@Parameter(description = "ID de um restaurante", example = "1", required = true) Long restauranteId);

	@Operation(summary = "Inativa um restaurante por ID", responses = {
			@ApiResponse(responseCode = "204", description = "Restaurante inativado"),
			@ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(schema = @Schema(ref = "Problema")))
	})
	ResponseEntity<Void> inativar(@Parameter(description = "ID de um restaurante", example = "1", required = true) Long restauranteId);

	@Operation(summary = "Ativa multiplos restaurantes por IDs", responses = {
			@ApiResponse(responseCode = "204", description = "Restaurantes ativados com sucesso")
			
	})
	void ativarMultiplos(@RequestBody(description = "IDs de restaurantes", required = true) List<Long> restauranteIds);

	@Operation(summary = "Desativa multiplos restaurantes por IDs", responses = {
			@ApiResponse(responseCode = "204", description = "Restaurantes desativados com sucesso")
			
	})
	void inativarMultiplos(@RequestBody(description = "IDs de restaurantes", required = true) List<Long> restauranteIds);

	@Operation(summary = "Fecha um restaurante por ID", responses = {
			@ApiResponse(responseCode = "204", description = "Restaurante fechado com sucesso"),
			@ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(schema = @Schema(ref = "Problema")))
	})
	ResponseEntity<Void> fechado(@Parameter(description = "ID de um restaurante", example = "1", required = true) Long restauranteId);

	@Operation(summary = "Abre um restaurante por ID", responses = {
			@ApiResponse(responseCode = "204", description = "Restaurante aberto com sucesso"),
			@ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(schema = @Schema(ref = "Problema")))
	})
	ResponseEntity<Void> aberto(@Parameter(description = "ID de um restaurante", example = "1", required = true) Long restauranteId);
}
