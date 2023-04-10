package com.algaworks.algafood.api.v2.controller;

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

import com.algaworks.algafood.api.ResourceUriHelper;
import com.algaworks.algafood.api.v2.assembler.CidadeDTOAssemblerV2;
import com.algaworks.algafood.api.v2.assembler.CidadeInputDisassemblerV2;
import com.algaworks.algafood.api.v2.model.CidadeDTOV2;
import com.algaworks.algafood.api.v2.model.input.CidadeInputV2;
import com.algaworks.algafood.api.v2.service.CidadeServiceV2;
import com.algaworks.algafood.domain.model.Cidade;

@RestController
@RequestMapping(path = "/v2/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeControllerV2 {

	@Autowired
	private CidadeServiceV2 cidadeService;

	@Autowired
	private CidadeDTOAssemblerV2 assembler;
	
	@Autowired 
	private CidadeInputDisassemblerV2 disassembler;
	
	@GetMapping
	public CollectionModel<CidadeDTOV2> listar() {
		return assembler.toCollectionModel(cidadeService.findAll());
	}

	@GetMapping("/{cidadeId}")
	public CidadeDTOV2 buscar(@PathVariable Long cidadeId) {
		return assembler.toModel(cidadeService.findByIdOrNotFound(cidadeId));
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public CidadeDTOV2 adicionar(@RequestBody @Valid CidadeInputV2 cidadeInput) {
		
		CidadeDTOV2 cidadeDTO = cidadeService.salvar(disassembler.toDomainModel(cidadeInput));
		
		ResourceUriHelper.addUriInResponseHeader(cidadeDTO.getId());
		
		return cidadeDTO;
	}

	@PutMapping("/{cidadeId}")
	public CidadeDTOV2 atualizar(@PathVariable Long cidadeId, @RequestBody @Valid CidadeInputV2 cidadeInput) {

		Cidade cidadeAtual = cidadeService.findByIdOrNotFound(cidadeId);
		disassembler.copyToDomainModel(cidadeInput, cidadeAtual);

		return cidadeService.salvar(cidadeAtual);
	}

	@DeleteMapping("/{cidadeId}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long cidadeId) {
		cidadeService.remover(cidadeId);
	}
}
