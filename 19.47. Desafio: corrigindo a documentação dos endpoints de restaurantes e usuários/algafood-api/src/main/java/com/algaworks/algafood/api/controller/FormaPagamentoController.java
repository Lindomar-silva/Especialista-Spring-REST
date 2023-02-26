package com.algaworks.algafood.api.controller;

import java.time.OffsetDateTime;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import com.algaworks.algafood.api.assembler.FormaPagamentoDTOAssembler;
import com.algaworks.algafood.api.assembler.FormaPagamentoInputDisassembler;
import com.algaworks.algafood.api.model.FormaPagamentoDTO;
import com.algaworks.algafood.api.model.input.FormaPagamentoInput;
import com.algaworks.algafood.api.openapi.controller.FormaPagamentoControllerOpenApi;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;
import com.algaworks.algafood.domain.service.FormaPagamentoService;

@RestController
@RequestMapping(path = "/formas-pagamentos", produces = MediaType.APPLICATION_JSON_VALUE)
public class FormaPagamentoController implements FormaPagamentoControllerOpenApi {

	@Autowired
	private FormaPagamentoService service;

	@Autowired
	private FormaPagamentoDTOAssembler assembler;
	
	@Autowired
	private FormaPagamentoInputDisassembler disassembler;

	@Autowired
	private FormaPagamentoRepository repository;
	
	@GetMapping
	public ResponseEntity<CollectionModel<FormaPagamentoDTO>> listar(ServletWebRequest request) {
		// Deabilitando ShallowEtagHeaderFilter para implementar requisições condicionais com Deep ETags
		ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());
		
		String eTag = "0";
		
		OffsetDateTime dataUltimaAtualizacao = repository.getDataUltimaAtualizacao();
		
		if (dataUltimaAtualizacao != null) {
			eTag = String.valueOf(dataUltimaAtualizacao.toEpochSecond());
		}
		
		if(request.checkNotModified(eTag)) {
			return null;
		}
		
		CollectionModel<FormaPagamentoDTO> formaPagamentoDTO = assembler.toCollectionModel(service.findAll());
		
		return ResponseEntity.ok()
				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePublic())
				.eTag(eTag)
				.body(formaPagamentoDTO);
	}

	@GetMapping("/{formaPagamentoId}")
	public ResponseEntity<FormaPagamentoDTO> buscar(@PathVariable Long formaPagamentoId, ServletWebRequest request) {
		ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());
		
		String eTag = "0";
		OffsetDateTime dataAtualizacao = repository.getDataAtualizacaoById(formaPagamentoId);
		
		if (dataAtualizacao != null) {
			eTag = String.valueOf(dataAtualizacao.toEpochSecond());
		}

		if(request.checkNotModified(eTag)) {
			return null;
		}
		
		FormaPagamentoDTO formaPagamentoDTO = assembler.toModel(service.findByIdOrNotFound(formaPagamentoId));
		
		return ResponseEntity.ok()
				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePrivate())
				.eTag(eTag)
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
