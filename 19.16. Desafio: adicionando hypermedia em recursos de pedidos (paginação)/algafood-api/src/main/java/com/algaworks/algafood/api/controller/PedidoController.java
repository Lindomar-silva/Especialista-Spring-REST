package com.algaworks.algafood.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.PedidoDTOAssembler;
import com.algaworks.algafood.api.assembler.PedidoDisassembler;
import com.algaworks.algafood.api.assembler.PedidoResumoDTOAssembler;
import com.algaworks.algafood.api.model.PedidoDTO;
import com.algaworks.algafood.api.model.PedidoResumoDTO;
import com.algaworks.algafood.api.model.input.PedidoInput;
import com.algaworks.algafood.api.openapi.controller.PedidoControllerOpenApi;
import com.algaworks.algafood.core.data.PageableTranslator;
import com.algaworks.algafood.domain.filter.PedidoFilter;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.service.PedidoService;
import com.google.common.collect.ImmutableBiMap;

@RestController
@RequestMapping(path = "/pedidos", produces = MediaType.APPLICATION_JSON_VALUE)
public class PedidoController implements PedidoControllerOpenApi {

	@Autowired
	private PedidoService service;

	@Autowired
	private PedidoDTOAssembler assembler;

	@Autowired	
	private PedidoDisassembler disassembler;

	@Autowired
	private PedidoResumoDTOAssembler resumoDTOAssembler;
	
	@Autowired
	private PagedResourcesAssembler<Pedido> pagedResourcesAssembler;
	
	@GetMapping
	public PagedModel<PedidoResumoDTO> pesquisar(PedidoFilter filtro, @PageableDefault(size = 10) Pageable pageable) {
		
		pageable = translateToSortPageable(pageable);
		Page<Pedido> pedidosPage = service.findAll(filtro, pageable);
		
		PagedModel<PedidoResumoDTO> pedidoPagedModel = pagedResourcesAssembler
				.toModel(pedidosPage, resumoDTOAssembler);
		
		return pedidoPagedModel;
	}

	@GetMapping("/{pedidoUuId}")
	public PedidoDTO buscar(@PathVariable String pedidoUuId) {
		return assembler.toModel(service.findByIdOrNotFound(pedidoUuId));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PedidoDTO emitir(@RequestBody @Valid PedidoInput pedidoInput) {
		Pedido novoPedido = disassembler.toDomainModel(pedidoInput);

		// TODO Usar usuário autenticado
		novoPedido.setCliente(new Usuario());
		novoPedido.getCliente().setId(3L);
			
		return assembler.toModel(service.emitir(novoPedido));
	}
	
	private Pageable translateToSortPageable(Pageable pageable) {
		var dePara = ImmutableBiMap.of(
				"uuidCod", "uuidCod",
				"restaurante.nome", "restaurante.nome",
				"nomeCliente", "cliente.nome",
				"valorTotal", "valorTotal"
			);
		
		return PageableTranslator.translate(pageable, dePara);
	}
}
