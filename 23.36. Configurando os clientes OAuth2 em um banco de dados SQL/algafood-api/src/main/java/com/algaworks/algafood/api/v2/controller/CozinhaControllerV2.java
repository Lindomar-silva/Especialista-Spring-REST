package com.algaworks.algafood.api.v2.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
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

import com.algaworks.algafood.api.v2.assembler.CozinhaDTOAssemblerV2;
import com.algaworks.algafood.api.v2.assembler.CozinhaInputDisassemblerV2;
import com.algaworks.algafood.api.v2.model.CozinhaDTOV2;
import com.algaworks.algafood.api.v2.model.input.CozinhaInputV2;
import com.algaworks.algafood.api.v2.openapi.controller.CozinhaControllerV2OpenApi;
import com.algaworks.algafood.api.v2.service.CadastroCozinhaServiceV2;
import com.algaworks.algafood.domain.model.Cozinha;

@RestController
@RequestMapping(value = "/v2/cozinhas", produces = MediaType.APPLICATION_JSON_VALUE)
public class CozinhaControllerV2 implements CozinhaControllerV2OpenApi {

	@Autowired
	private CadastroCozinhaServiceV2 service;

	@Autowired
	private CozinhaDTOAssemblerV2 assembler;
	
	@Autowired
	private CozinhaInputDisassemblerV2 disassembler;
	
	@Autowired
	private PagedResourcesAssembler<Cozinha> pagedResourcesAssembler;
	
	@GetMapping
	public PagedModel<CozinhaDTOV2> listar(@PageableDefault(size = 10) Pageable pageable) {
		Page<Cozinha> cozinhasPage = service.findAll(pageable);
//		List<CozinhaDTO> cozinhaDTOs = assembler.toCollectionModel(cozinhasPage.getContent());

//		return new PageImpl<>(cozinhaDTOs, pageable, cozinhasPage.getTotalElements());
		
		PagedModel<CozinhaDTOV2> cozinhaPagedModel = pagedResourcesAssembler
				.toModel(cozinhasPage, assembler);
		
		return cozinhaPagedModel;
	}

	@GetMapping("/{cozinhaId}")
	public CozinhaDTOV2 buscar(@PathVariable Long cozinhaId) {
		return assembler.toModel(service.findByIdOrNotFound(cozinhaId));
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public CozinhaDTOV2 adicionar(@RequestBody @Valid CozinhaInputV2 cozinhaInput) {
		return service.salvar(disassembler.toDomainModel(cozinhaInput));
	}

	@PutMapping("/{cozinhaId}")
	public CozinhaDTOV2 atualizar(@PathVariable Long cozinhaId, @Valid @RequestBody CozinhaInputV2 cozinhaInput) {

		Cozinha cozinhaAtual = service.findByIdOrNotFound(cozinhaId);
//		BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");
		disassembler.copyToDomainModel(cozinhaInput, cozinhaAtual);
		
		return service.salvar(cozinhaAtual);
	}

	@DeleteMapping("/{cozinhaId}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long cozinhaId) {
		service.remover(cozinhaId);
	}
}