package com.algaworks.algafood.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.assembler.PermissaoAssembler;
import com.algaworks.algafood.api.model.PermissaoDTO;
import com.algaworks.algafood.api.openapi.controller.GrupoPermissaoControllerOpenApi;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.service.GrupoService;

@RestController
@RequestMapping(value = "/grupos/{grupoId}/permissoes", produces = MediaType.APPLICATION_JSON_VALUE)
public class GrupoPermissaoController implements GrupoPermissaoControllerOpenApi {

	@Autowired
	private GrupoService service;

	@Autowired
	private PermissaoAssembler assembler;

	@Autowired
	private AlgaLinks algaLinks;
	
	@Override
	@GetMapping
	public CollectionModel<PermissaoDTO> listar(@PathVariable Long grupoId) {
		Grupo grupo = service.findByIdOrNotFound(grupoId);
		CollectionModel<PermissaoDTO> permissoesModel
			= assembler.toCollectionModel(grupo.getPermissaos())
				.removeLinks()
				.add(algaLinks.linkToGrupoPermissoes(grupoId))
				.add(algaLinks.linkToGrupoPermissaoAssociacao(grupoId, "associar"));
		
		permissoesModel.getContent().forEach(permissaoModel -> {
			permissaoModel.add(algaLinks.linkToGrupoPermissaoDesassociacao(grupoId, permissaoModel.getId(), "desassociar"));
		});
		
		return permissoesModel;
	}

	@Override
	@PutMapping("/{permissaoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> associar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
		service.associarPermissao(grupoId, permissaoId);
		return ResponseEntity.noContent().build();
	}
	
	@Override
	@DeleteMapping("/{permissaoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> desassociar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
		service.desassociarPermissao(grupoId, permissaoId);
		return ResponseEntity.noContent().build();
	}
}