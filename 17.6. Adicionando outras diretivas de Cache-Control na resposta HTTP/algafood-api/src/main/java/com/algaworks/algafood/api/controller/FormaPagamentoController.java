package com.algaworks.algafood.api.controller;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/formas-pagamentos")
public class FormaPagamentoController {

	@Autowired
	private FormaPagamentoService service;

	@Autowired
	private FormaPagamentoDTOAssembler assembler;
	
	@Autowired
	private FormaPagamentoInputDisassembler disassembler;

	@GetMapping
	public ResponseEntity<List<FormaPagamentoDTO>> listar() {
		List<FormaPagamentoDTO> formaPagamentoDTO = assembler.toCollectionDTO(service.findAll());
		
		return ResponseEntity.ok()
//				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
//				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePrivate())
				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePublic())
//				.cacheControl(CacheControl.noCache())
//				.cacheControl(CacheControl.noStore())
				.body(formaPagamentoDTO);
	}

	@GetMapping("/{formaPagamentoId}")
	public ResponseEntity<FormaPagamentoDTO> buscar(@PathVariable Long formaPagamentoId) {
		FormaPagamentoDTO formaPagamentoDTO = assembler.toDTO(service.findByIdOrNotFound(formaPagamentoId));
		
		return ResponseEntity.ok()
				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePrivate())
				.body(formaPagamentoDTO);
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
