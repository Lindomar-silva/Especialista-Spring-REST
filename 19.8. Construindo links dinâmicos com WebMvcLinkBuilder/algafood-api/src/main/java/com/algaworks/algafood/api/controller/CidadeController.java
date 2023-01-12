package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
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
import com.algaworks.algafood.api.assembler.CidadeDTOAssembler;
import com.algaworks.algafood.api.assembler.CidadeInputDisassembler;
import com.algaworks.algafood.api.model.CidadeDTO;
import com.algaworks.algafood.api.model.input.CidadeInput;
import com.algaworks.algafood.api.openapi.controller.CidadeControllerOpenApi;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.service.CidadeService;

@RestController
@RequestMapping(path = "/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeController implements CidadeControllerOpenApi {

	@Autowired
	private CidadeService cidadeService;

	@Autowired
	private CidadeDTOAssembler assembler;
	
	@Autowired 
	private CidadeInputDisassembler disassembler;
	
	@GetMapping
	public List<CidadeDTO> listar() {
		return assembler.toCollectionDTO(cidadeService.findAll());
	}

	@Override
	@GetMapping("/{cidadeId}")
	public CidadeDTO buscar(@PathVariable Long cidadeId) {
		CidadeDTO cidadeDTO = assembler.toDTO(cidadeService.findByIdOrNotFound(cidadeId));
		
		cidadeDTO.add(WebMvcLinkBuilder.linkTo(CidadeController.class)
				.slash(cidadeDTO.getId()).withSelfRel());
		
//		cidadeDTO.add(Link.of("http://localhost:8080/cidades/2"));
		
		cidadeDTO.add(WebMvcLinkBuilder.linkTo(CidadeController.class)
				.withRel("cidades"));
		
//		cidadeDTO.add(Link.of("http://localhost:8080/cidades", "cidades"));
		
		cidadeDTO.add(WebMvcLinkBuilder.linkTo(EstadoController.class)
				.slash(cidadeDTO.getEstado().getId()).withSelfRel());
		
//		cidadeDTO.getEstado().add(Link.of("http://localhost:8080/estados/1"));
		
		return cidadeDTO;
	}

	@Override
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public CidadeDTO adicionar(@RequestBody @Valid CidadeInput cidadeInput) {
		
		CidadeDTO cidadeDTO = cidadeService.salvar(disassembler.toDomainModel(cidadeInput));
		
		ResourceUriHelper.addUriInResponseHeader(cidadeDTO.getId());
		
		return cidadeDTO;
	}

	@Override
	@PutMapping("/{cidadeId}")
	public CidadeDTO atualizar(@PathVariable Long cidadeId, @RequestBody @Valid CidadeInput cidadeInput) {

		Cidade cidadeAtual = cidadeService.findByIdOrNotFound(cidadeId);
		disassembler.copyToDomainModel(cidadeInput, cidadeAtual);

		return cidadeService.salvar(cidadeAtual);
	}

	@Override
	@DeleteMapping("/{cidadeId}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long cidadeId) {
		cidadeService.remover(cidadeId);
	}
}
