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

import com.algaworks.algafood.api.v1.assembler.EstadoDTOAssembler;
import com.algaworks.algafood.api.v1.assembler.EstadoInputDisassembler;
import com.algaworks.algafood.api.v1.model.EstadoDTO;
import com.algaworks.algafood.api.v1.model.input.EstadoInput;
import com.algaworks.algafood.api.v1.openapi.controller.EstadoControllerOpenApi;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.service.EstadoService;

@RestController
@RequestMapping(path = "/estados", produces = MediaType.APPLICATION_JSON_VALUE)
public class EstadoController implements EstadoControllerOpenApi {

	@Autowired
	private EstadoService estadoService;

	@Autowired
	private EstadoDTOAssembler assembler;
	
	@Autowired
	private EstadoInputDisassembler disassembler;
	
	@GetMapping
	public CollectionModel<EstadoDTO> listar() {
		return assembler.toCollectionModel(estadoService.findAll());
	}

	@GetMapping("/{estadoId}")
	public EstadoDTO buscar(@PathVariable Long estadoId) {
		return assembler.toModelWithCollectionRel(estadoService.findByIdOrNotFound(estadoId));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EstadoDTO adicionar(@RequestBody @Valid EstadoInput estadoInput) {
		return estadoService.salvar(disassembler.toDomainModel(estadoInput));
	}

	@PutMapping("/{estadoId}")
	public EstadoDTO atualizar(@PathVariable Long estadoId, @RequestBody @Valid EstadoInput estadoInput) {
		Estado estadoAtual = estadoService.findByIdOrNotFound(estadoId);
		disassembler.copyToDomainModel(estadoInput, estadoAtual);
		
		return estadoService.salvar(estadoAtual);
	}

	@DeleteMapping("/{estadoId}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long estadoId) {
		estadoService.remover(estadoId);
	}
}