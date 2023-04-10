package com.algaworks.algafood.api.v1.controller;

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

import com.algaworks.algafood.api.v1.assembler.CozinhaDTOAssembler;
import com.algaworks.algafood.api.v1.assembler.CozinhaInputDisassembler;
import com.algaworks.algafood.api.v1.model.CozinhaDTO;
import com.algaworks.algafood.api.v1.model.input.CozinhaInput;
import com.algaworks.algafood.api.v1.openapi.controller.CozinhaControllerOpenApi;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;

@RestController
@RequestMapping(value = "/v1/gastronomias", produces = MediaType.APPLICATION_JSON_VALUE)
public class CozinhaController implements CozinhaControllerOpenApi {

	@Autowired
	private CadastroCozinhaService service;

	@Autowired
	private CozinhaDTOAssembler assembler;
	
	@Autowired
	private CozinhaInputDisassembler disassembler;
	
	@Autowired
	private PagedResourcesAssembler<Cozinha> pagedResourcesAssembler;
	
	@GetMapping
	public PagedModel<CozinhaDTO> listar(@PageableDefault(size = 10) Pageable pageable) {
		Page<Cozinha> cozinhasPage = service.findAll(pageable);
//		List<CozinhaDTO> cozinhaDTOs = assembler.toCollectionModel(cozinhasPage.getContent());

//		return new PageImpl<>(cozinhaDTOs, pageable, cozinhasPage.getTotalElements());
		
		PagedModel<CozinhaDTO> cozinhaPagedModel = pagedResourcesAssembler
				.toModel(cozinhasPage, assembler);
		
		return cozinhaPagedModel;
	}

	@GetMapping("/{cozinhaId}")
	public CozinhaDTO buscar(@PathVariable Long cozinhaId) {
		return assembler.toModel(service.findByIdOrNotFound(cozinhaId));
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public CozinhaDTO adicionar(@RequestBody @Valid CozinhaInput cozinhaInput) {
		return service.salvar(disassembler.toDomainModel(cozinhaInput));
	}

	@PutMapping("/{cozinhaId}")
	public CozinhaDTO atualizar(@PathVariable Long cozinhaId, @Valid @RequestBody CozinhaInput cozinhaInput) {

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