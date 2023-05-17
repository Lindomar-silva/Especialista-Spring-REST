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

import com.algaworks.algafood.api.ResourceUriHelper;
import com.algaworks.algafood.api.v1.assembler.CidadeDTOAssembler;
import com.algaworks.algafood.api.v1.assembler.CidadeInputDisassembler;
import com.algaworks.algafood.api.v1.model.CidadeDTO;
import com.algaworks.algafood.api.v1.model.input.CidadeInput;
import com.algaworks.algafood.api.v1.openapi.controller.CidadeControllerOpenApi;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.service.CidadeService;

@RestController
@RequestMapping(path = "/v1/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeController implements CidadeControllerOpenApi {

	@Autowired
	private CidadeService cidadeService;

	@Autowired
	private CidadeDTOAssembler assembler;
	
	@Autowired 
	private CidadeInputDisassembler disassembler;
	
	@CheckSecurity.Cidades.PodeConsultar
	@Override
	@GetMapping
	public CollectionModel<CidadeDTO> listar() {
		return assembler.toCollectionModel(cidadeService.findAll());
	}

	@CheckSecurity.Cidades.PodeConsultar
	@Override
	@GetMapping("/{cidadeId}")
	public CidadeDTO buscar(@PathVariable Long cidadeId) {
		return assembler.toModel(cidadeService.findByIdOrNotFound(cidadeId));
	}

	@CheckSecurity.Cidades.PodeEditar
	@Override
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public CidadeDTO adicionar(@RequestBody @Valid CidadeInput cidadeInput) {
		
		CidadeDTO cidadeDTO = cidadeService.salvar(disassembler.toDomainModel(cidadeInput));
		
		ResourceUriHelper.addUriInResponseHeader(cidadeDTO.getId());
		
		return cidadeDTO;
	}

	@CheckSecurity.Cidades.PodeEditar
	@Override
	@PutMapping("/{cidadeId}")
	public CidadeDTO atualizar(@PathVariable Long cidadeId, @RequestBody @Valid CidadeInput cidadeInput) {

		Cidade cidadeAtual = cidadeService.findByIdOrNotFound(cidadeId);
		disassembler.copyToDomainModel(cidadeInput, cidadeAtual);

		return cidadeService.salvar(cidadeAtual);
	}

	@CheckSecurity.Cidades.PodeEditar
	@Override
	@DeleteMapping("/{cidadeId}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long cidadeId) {
		cidadeService.remover(cidadeId);
	}
}
