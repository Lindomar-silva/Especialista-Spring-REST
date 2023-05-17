package com.algaworks.algafood.api.v1.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.v1.model.UsuarioDTO;
import com.algaworks.algafood.api.v1.model.input.SenhaInput;
import com.algaworks.algafood.api.v1.model.input.UsuarioComSenhaInput;
import com.algaworks.algafood.api.v1.model.input.UsuarioInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Usuários")
public interface UsuarioControllerOpenAPi {

	@ApiOperation("Lista os usuários")
	CollectionModel<UsuarioDTO> listar();

	@ApiOperation("Busca um usuário por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "400", description = "ID do usuário inválido", 
				content = @Content(schema = @Schema(implementation = Problem.class))),
		@ApiResponse(responseCode = "404", description = "Usuário não encontrado", 
				content = @Content(schema = @Schema(implementation = Problem.class)))
	})
	UsuarioDTO buscar(@ApiParam(value = "ID do usuário", example = "1", required = true) Long usuarioId);

	@ApiOperation("Cadastra um usuário")
	@ApiResponse(responseCode = "201", description = "Usuário cadastrado com sucesso")
	UsuarioDTO adicionar(@ApiParam(name = "corpo", value = "Representação de um usuário", required = true) UsuarioComSenhaInput usuarioComSenhaInput);

	@ApiOperation("Atualiza um usuário por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
		@ApiResponse(responseCode = "404", description = "Usuário não encontrado", 
				content = @Content(schema = @Schema(implementation = Problem.class)))
	})
	UsuarioDTO atualizar(@ApiParam(value = "ID do usuário", example = "1", required = true) Long usuarioId, 
			@ApiParam(name = "corpo", value = "Representação de um usuário com novos dados", required = true) UsuarioInput usuarioInput);
	
	@ApiOperation("Exclui um usuário por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "204", description = "Usuário excluido com sucesso"),
		@ApiResponse(responseCode = "404", description = "Usuário não encontrado", 
				content = @Content(schema = @Schema(implementation = Problem.class)))
	})
	void remover(@ApiParam(value = "ID do usuário", example = "1", required = true) Long usuarioId);
	
	@ApiOperation("Atualiza a senha um usuário por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "204", description = "Senha alterada com sucesso"),
		@ApiResponse(responseCode = "404", description = "Usuário não encontrado", 
				content = @Content(schema = @Schema(implementation = Problem.class)))
	})
	void AlterarSenha(@ApiParam(value = "ID do usuário", example = "1", required = true) Long usuarioId, 
			@ApiParam(name = "corpo", value = "Representação de uma nova senha", required = true) SenhaInput senhaInput);
}
