package com.algaworks.algafood.api.v1.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.algaworks.algafood.api.v1.model.PermissaoDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@SecurityRequirement(name = "security_auth")
@Tag(name = "Grupos")
public interface GrupoPermissaoControllerOpenApi {

	@Operation(summary = "Lista as permissões associadas a um grupo por ID", responses = {
			@ApiResponse(responseCode = "200"),
			@ApiResponse(responseCode = "400", description = "ID do grupo inválido", content = @Content(schema = @Schema(ref = "Problema"))),
			@ApiResponse(responseCode = "404", description = "Grupo não encontrado", content = @Content(schema = @Schema(ref = "Problema")))
	})
	CollectionModel<PermissaoDTO> listar(@Parameter(description = "ID do grupo", example = "1", required = true) Long grupoId);

	@Operation(summary = "Associação de permissão com grupo", responses = {
			@ApiResponse(responseCode = "204", description = "Associação realizada com sucesso"),
			@ApiResponse(responseCode = "404", description = "Grupo ou permissão não encontrado", content = @Content(schema = @Schema(ref = "Problema")))
	})
	ResponseEntity<Void> associar(@Parameter(description = "ID do grupo", example = "1", required = true) Long grupoId, 
			@Parameter(description = "ID da permissão", example = "1", required = true) Long permissaoId);

	@Operation(summary = "Desassociação de permissão com grup", responses = {
			@ApiResponse(responseCode = "204", description = "Desassociação realizada com sucesso"),
			@ApiResponse(responseCode = "404", description = "Grupo ou permissão não encontrado", content = @Content(schema = @Schema(ref = "Problema")))
	})
	ResponseEntity<Void> desassociar(@Parameter(description = "ID do grupo", example = "1", required = true) Long grupoId, 
			@Parameter(description = "ID da permissão", example = "1", required = true) Long permissaoId);
}
