package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.CidadeDTOAssembler;
import com.algaworks.algafood.api.assembler.CidadeInputDisassembler;
import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.CidadeDTO;
import com.algaworks.algafood.api.model.input.CidadeInput;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.service.CidadeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Cidades")
@RestController
@RequestMapping("/cidades")
public class CidadeController {

	@Autowired
	private CidadeService cidadeService;

	@Autowired
	private CidadeDTOAssembler assembler;
	
	@Autowired 
	private CidadeInputDisassembler disassembler;
	
	@ApiOperation("Lista as cidades")
	@GetMapping
	public List<CidadeDTO> listar() {
		return assembler.toCollectionDTO(cidadeService.findAll());
	}

	@ApiOperation("Busca uma cidade por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "500", description = "ID da cidade inválido", 
				content = @Content(schema = @Schema(implementation = Problem.class))),
		@ApiResponse(responseCode = "404", description = "Cidade não encontrada", 
				content = @Content(schema = @Schema(implementation = Problem.class)))
	})
	@GetMapping("/{cidadeId}")
	public CidadeDTO buscar(@ApiParam(value = "ID da cidade", example = "1") @PathVariable Long cidadeId) {
		return assembler.toDTO(cidadeService.findByIdOrNotFound(cidadeId));
	}

	@ApiOperation("Cadastra uma cidade")
	@ApiResponse(responseCode = "201", description = "Cidade cadastrada")
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public CidadeDTO adicionar(@ApiParam(name = "corpo", value = "Representação de uma cidade")
	@RequestBody @Valid CidadeInput cidadeInput) {
		return cidadeService.salvar(disassembler.toDomainModel(cidadeInput));
	}

	@ApiOperation("Atualiza uma cidade por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Cidade atualizada"),
		@ApiResponse(responseCode = "404", description = "Cidade não encontrada", 
				content = @Content(schema = @Schema(implementation = Problem.class)))
	})
	@PutMapping("/{cidadeId}")
	public CidadeDTO atualizar(@ApiParam(value = "ID da cidade", example = "1") @PathVariable Long cidadeId, 
	@ApiParam(name = "corpo", value = "Representação de uma cidade com novos dados") @RequestBody @Valid CidadeInput cidadeInput) {

		Cidade cidadeAtual = cidadeService.findByIdOrNotFound(cidadeId);
		disassembler.copyToDomainModel(cidadeInput, cidadeAtual);

		return cidadeService.salvar(cidadeAtual);
	}

	@ApiOperation("Exclui uma cidade por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "204", description = "Cidade Excluida"),
		@ApiResponse(responseCode = "404", description = "Cidade não encontrada", 
				content = @Content(schema = @Schema(implementation = Problem.class)))
	})
	@DeleteMapping("/{cidadeId}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void remover(@ApiParam(value = "ID da cidade", example = "1") @PathVariable Long cidadeId) {
		cidadeService.remover(cidadeId);
	}
}
