package com.algaworks.algafood.api.v1.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.v1.assembler.GrupoAssembler;
import com.algaworks.algafood.api.v1.assembler.GrupoInputDisassembler;
import com.algaworks.algafood.api.v1.model.GrupoDTO;
import com.algaworks.algafood.api.v1.model.input.GrupoInput;
import com.algaworks.algafood.api.v1.openapi.controller.GrupoControllerOpenApi;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.service.GrupoService;

@RestController
@RequestMapping(path = "/grupos", produces = MediaType.APPLICATION_JSON_VALUE)
public class GrupoController implements GrupoControllerOpenApi {

	@Autowired
	private GrupoService service;

	@Autowired
	private GrupoAssembler assembler;

	@Autowired
	private GrupoInputDisassembler disassembler;

	@GetMapping
	public CollectionModel<GrupoDTO> listar() {
		return assembler.toCollectionModel(service.findAll());
	}

	@GetMapping("/{grupoId}")
	public GrupoDTO buscar(@PathVariable Long grupoId) {
		return assembler.toModel(service.findByIdOrNotFound(grupoId));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public GrupoDTO adicionar(@RequestBody @Valid GrupoInput grupoInput) {
		return service.salvar(disassembler.toDomainModel(grupoInput));
	}

	@PutMapping("/{grupoId}")
	public GrupoDTO atualizar(@PathVariable Long grupoId, @RequestBody @Valid GrupoInput grupoInput) {
		Grupo grupoAtual = service.findByIdOrNotFound(grupoId);
		disassembler.copyDomainModel(grupoInput, grupoAtual);

		return service.salvar(grupoAtual);
	}

	@DeleteMapping("/{grupoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long grupoId) {
		service.remover(grupoId);
	}
}
