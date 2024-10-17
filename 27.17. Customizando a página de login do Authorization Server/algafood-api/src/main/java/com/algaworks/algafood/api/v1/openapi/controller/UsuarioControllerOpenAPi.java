package com.algaworks.algafood.api.v1.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import com.algaworks.algafood.api.v1.model.UsuarioDTO;
import com.algaworks.algafood.api.v1.model.input.SenhaInput;
import com.algaworks.algafood.api.v1.model.input.UsuarioComSenhaInput;
import com.algaworks.algafood.api.v1.model.input.UsuarioInput;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@SecurityRequirement(name = "security_auth")
@Tag(name = "Usuários",description = "Gerencia os usuários")
public interface UsuarioControllerOpenAPi {

	@Operation(summary = "Lista os usuários")
	CollectionModel<UsuarioDTO> listar();

	@Operation(summary = "Busca um usuário por ID", responses = {
			@ApiResponse(responseCode = "200"),
			@ApiResponse(responseCode = "400", description = "ID do usuário inválido", content = @Content(schema = @Schema(ref = "Problema"))),
			@ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content(schema = @Schema(ref = "Problema")))
	})
	UsuarioDTO buscar(@Parameter(description = "ID do usuário", example = "1", required = true) Long usuarioId);

	@Operation(summary = "Cadastra um usuário", responses = @ApiResponse(responseCode = "201", description = "Usuário cadastrado"))
	UsuarioDTO adicionar(@RequestBody(description = "Representação de um novo usuário", required = true) UsuarioComSenhaInput usuarioComSenhaInput);

	@Operation(summary = "Atualiza um usuário por ID", responses = {
			@ApiResponse(responseCode = "204", description = "Usuário atualizado"),
			@ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content(schema = @Schema(ref = "Problema")))
	})
	UsuarioDTO atualizar(@Parameter(description = "ID do usuário", example = "1", required = true) Long usuarioId, 
			@RequestBody(description = "Representação de um usuário atualizado", required = true) UsuarioInput usuarioInput);

	@Operation(summary = "Exclui um usuário por ID", responses = {
			@ApiResponse(responseCode = "204", description = "Usuário excluído"),
			@ApiResponse(responseCode = "400", description = "ID do Usuário inválido", content = @Content(schema = @Schema(ref = "Problema"))),
			@ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content(schema = @Schema(ref = "Problema")))
	})
	void remover(@Parameter(description = "ID do usuário", example = "1", required = true) Long usuarioId);

	@Operation(summary = "Atualiza a senha de um usuário por ID", responses = {
			@ApiResponse(responseCode = "204", description = "Senha alterada com sucesso"),
			@ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content(schema = @Schema(ref = "Problema")))
	})
	void AlterarSenha(@Parameter(description = "ID do usuário", example = "1", required = true) Long usuarioId, 
			@Parameter(description = "Nova senha do usuário", example = "1", required = true) SenhaInput senhaInput);
}
