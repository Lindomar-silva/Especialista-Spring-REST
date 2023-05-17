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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.v1.assembler.PedidoDTOAssembler;
import com.algaworks.algafood.api.v1.assembler.PedidoDisassembler;
import com.algaworks.algafood.api.v1.assembler.PedidoResumoDTOAssembler;
import com.algaworks.algafood.api.v1.model.PedidoDTO;
import com.algaworks.algafood.api.v1.model.PedidoResumoDTO;
import com.algaworks.algafood.api.v1.model.input.PedidoInput;
import com.algaworks.algafood.api.v1.openapi.controller.PedidoControllerOpenApi;
import com.algaworks.algafood.core.data.PageWrpper;
import com.algaworks.algafood.core.data.PageableTranslator;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.filter.PedidoFilter;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.service.PedidoService;
import com.google.common.collect.ImmutableBiMap;

@RestController
@RequestMapping(path = "/v1/pedidos", produces = MediaType.APPLICATION_JSON_VALUE)
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
	
	@Autowired
	private AlgaSecurity algaSecurity;
	
	@CheckSecurity.Pedidos.PodePesquisar
	@Override
	@GetMapping
	public PagedModel<PedidoResumoDTO> pesquisar(PedidoFilter filtro, @PageableDefault(size = 10) Pageable pageable) {
		
		Pageable pageableTraduzido = translateToSortPageable(pageable);
		Page<Pedido> pedidosPage = service.findAll(filtro, pageableTraduzido);
		
		pedidosPage = new PageWrpper<>(pedidosPage, pageable);
		
		PagedModel<PedidoResumoDTO> pedidoPagedModel = pagedResourcesAssembler
				.toModel(pedidosPage, resumoDTOAssembler);
		
		return pedidoPagedModel;
	}

	@CheckSecurity.Pedidos.PodeBuscar
	@Override
	@GetMapping("/{pedidoUuId}")
	public PedidoDTO buscar(@PathVariable String pedidoUuId) {
		return assembler.toModel(service.findByIdOrNotFound(pedidoUuId));
	}

	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PedidoDTO emitir(@RequestBody @Valid PedidoInput pedidoInput) {
		Pedido novoPedido = disassembler.toDomainModel(pedidoInput);

		novoPedido.setCliente(new Usuario());
		novoPedido.getCliente().setId(algaSecurity.getUsuarioId());
			
		return assembler.toModel(service.emitir(novoPedido));
	}
	
	private Pageable translateToSortPageable(Pageable pageable) {
		var dePara = ImmutableBiMap.of(
				"uuidCod", "uuidCod",
				"nomeRestaurante", "restaurante.nome",
				"nomeCliente", "cliente.nome",
				"valorTotal", "valorTotal"
			);
		
		return PageableTranslator.translate(pageable, dePara);
	}
}
