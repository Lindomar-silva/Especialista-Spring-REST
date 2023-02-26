package com.algaworks.algafood.api.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.FormaPagamentoDTO;
import com.algaworks.algafood.api.model.input.FormaPagamentoInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Formas de Pagamento")
public interface FormaPagamentoControllerOpenApi {

	@ApiOperation("Lista as formas de pagamento")
	ResponseEntity<CollectionModel<FormaPagamentoDTO>> listar(ServletWebRequest request);

	@ApiOperation("Busca uma forma de pagamento por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "400", description = "ID da forma de pagamento inválido", 
				content = @Content(schema = @Schema(implementation = Problem.class))),
		@ApiResponse(responseCode = "404", description = "Forma de pagamento não encontrada", 
				content = @Content(schema = @Schema(implementation = Problem.class)))
	})
	ResponseEntity<FormaPagamentoDTO> buscar(@ApiParam(value = "ID da forma de pagamento", 
		example = "1", required = true) Long formaPagamentoId, ServletWebRequest request);
	
	@ApiOperation("Cadastra uma forma de pagamento")
	@ApiResponse(responseCode = "201", description = "Forma de pagamento cadastrada")
	FormaPagamentoDTO adicionar(@ApiParam(name = "corpo", value = "Representação de uma forma de pagamento", required = true) FormaPagamentoInput formaPagamentoInput);
	
	
	@ApiOperation("Atualiza uma forma de pagamento por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Forma de pagamento atualizada"),
		@ApiResponse(responseCode = "404", description = "Forma de pagamento não encontrada", 
				content = @Content(schema = @Schema(implementation = Problem.class)))
	})
	FormaPagamentoDTO atualizar(@ApiParam(value = "ID da forma de paamento", example = "1", required = true) Long formaPagamentoId,
			@ApiParam(name = "corpo", value = "Representação de uma forma de pagamentos com novos dados", required = true)  FormaPagamentoInput formaPagamentoInput);
	
	@ApiOperation("Exclui uma forma de pagamento por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "204", description = "Forma de pagamento excluida"),
		@ApiResponse(responseCode = "404", description = "Forma de pagamento não encontrada", 
				content = @Content(schema = @Schema(implementation = Problem.class)))
	})
	void remover(@ApiParam(value = "ID da forma de pagamento", example = "1", required = true) Long formaPagamentoId);
}
