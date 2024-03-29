package com.algaworks.algafood.api.controller.openapi;

import java.util.List;
import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.CidadeDTO;
import com.algaworks.algafood.api.model.input.CidadeInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Cidades")
public interface CidadeControllerOpenApi {

	@ApiOperation("Lista as cidades")
	public List<CidadeDTO> listar();

	@ApiOperation("Busca uma cidade por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "400", description = "ID da cidade inválido", 
				content = @Content(schema = @Schema(implementation = Problem.class))),
		@ApiResponse(responseCode = "404", description = "Cidade não encontrada", 
				content = @Content(schema = @Schema(implementation = Problem.class)))
	})
	public CidadeDTO buscar(@ApiParam(value = "ID da cidade", example = "1") Long cidadeId);

	@ApiOperation("Cadastra uma cidade")
	@ApiResponse(responseCode = "201", description = "Cidade cadastrada")
	public CidadeDTO adicionar(@ApiParam(name = "corpo", value = "Representação de uma cidade") CidadeInput cidadeInput);
	
	@ApiOperation("Atualiza uma cidade por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Cidade atualizada"),
		@ApiResponse(responseCode = "404", description = "Cidade não encontrada", 
				content = @Content(schema = @Schema(implementation = Problem.class)))
	})
	public CidadeDTO atualizar(@ApiParam(value = "ID da cidade", example = "1") Long cidadeId, 
	@ApiParam(name = "corpo", value = "Representação de uma cidade com novos dados") CidadeInput cidadeInput);
	
	@ApiOperation("Exclui uma cidade por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "204", description = "Cidade Excluida"),
		@ApiResponse(responseCode = "404", description = "Cidade não encontrada", 
				content = @Content(schema = @Schema(implementation = Problem.class)))
	})
	public void remover(@ApiParam(value = "ID da cidade", example = "1") Long cidadeId);
}
