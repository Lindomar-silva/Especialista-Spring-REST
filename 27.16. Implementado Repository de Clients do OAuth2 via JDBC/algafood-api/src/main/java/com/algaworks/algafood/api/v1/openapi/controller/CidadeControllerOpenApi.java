package com.algaworks.algafood.api.v1.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import com.algaworks.algafood.api.v1.model.CidadeDTO;
import com.algaworks.algafood.api.v1.model.input.CidadeInput;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@SecurityRequirement(name = "security_auth")
@Tag(name = "Cidades") //, description = "Gerencia as cidades")
public interface CidadeControllerOpenApi {

	@Operation(summary = "Lista as cidades")
	CollectionModel<CidadeDTO> listar();

	@Operation(summary = "Busca uma cidade por ID", responses = {
			@ApiResponse(responseCode = "200"),
			@ApiResponse(responseCode = "400", description = "ID da cidade inválido", content = @Content(schema = @Schema(ref = "Problema"))),
			@ApiResponse(responseCode = "404", description = "Cidade não encontrada", content = @Content(schema = @Schema(ref = "Problema")))
	})			
	CidadeDTO buscar(@Parameter(description = "ID de uma cidade", example = "1", required = true) Long cidadeId);

	@Operation(summary = "Cadastra uma cidade", description = "Cadastro de cidade, necessita de um estado válido", 
			responses = @ApiResponse(responseCode = "201", description = "Cidade cadastrada"))
	CidadeDTO adicionar(@RequestBody(description = "Representação de uma nova cidade", required = true) CidadeInput cidadeInput);

	@Operation(summary = "Atualiza uma cidade por ID", responses = {
			@ApiResponse(responseCode = "200"),
			@ApiResponse(responseCode = "400", description = "ID da cidade inválido", content = @Content(schema = @Schema(ref = "Problema"))),
			@ApiResponse(responseCode = "404", description = "Cidade não encontrada", content = @Content(schema = @Schema(ref = "Problema")))
	})
	CidadeDTO atualizar(@Parameter(description = "ID de uma cidade", example = "1", required = true) Long cidadeId, 
			@RequestBody(description = "Representação de uma cidade com dados atualizados", required = true) CidadeInput cidadeInput);

	@Operation(summary = "Exclui uma cidade por ID", responses = {
			@ApiResponse(responseCode = "204", description = "Cidade excluída"),
			@ApiResponse(responseCode = "400", description = "ID da cidade inválido", content = @Content(schema = @Schema(ref = "Problema"))),
			@ApiResponse(responseCode = "404", description = "Cidade não encontrada", content = @Content(schema = @Schema(ref = "Problema")))
	})
	void remover(@Parameter(description = "ID de uma cidade") Long cidadeId);
}
