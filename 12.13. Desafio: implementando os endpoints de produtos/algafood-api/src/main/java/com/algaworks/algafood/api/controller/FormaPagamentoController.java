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

import com.algaworks.algafood.api.assembler.FormaPagamentoDTOAssembler;
import com.algaworks.algafood.api.assembler.FormaPagamentoInputDisassembler;
import com.algaworks.algafood.api.model.FormaPagamentoDTO;
import com.algaworks.algafood.api.model.input.FormaPagamentoInput;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.service.FormaPagamentoService;

@RestController
@RequestMapping("forma-pagamentos")
public class FormaPagamentoController {

	@Autowired
	private FormaPagamentoService service;

	@Autowired
	private FormaPagamentoDTOAssembler assembler;
	
	@Autowired
	private FormaPagamentoInputDisassembler disassembler;

	@GetMapping
	public List<FormaPagamentoDTO> listar() {
		return assembler.toCollectionDTO(service.findAll());
	}

	@GetMapping("/{formaPagamentoId}")
	public FormaPagamentoDTO buscar(@PathVariable Long formaPagamentoId) {
		return assembler.toDTO(service.findByIdOrNotFound(formaPagamentoId));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public FormaPagamentoDTO adicionar(@RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
		return service.salvar(disassembler.toDomainModel(formaPagamentoInput));
	}
	
	@PutMapping("/{formaPagamentoId}")
	public FormaPagamentoDTO atualizar(@PathVariable Long formaPagamentoId,
			@RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {

		FormaPagamento formaPagamentoAtual = service.findByIdOrNotFound(formaPagamentoId);
		disassembler.copyDomainModel(formaPagamentoInput, formaPagamentoAtual);

		return service.salvar(formaPagamentoAtual);
	}
	
	@DeleteMapping("/{formaPagamentoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long formaPagamentoId) {
		service.remover(formaPagamentoId);
	}
}
