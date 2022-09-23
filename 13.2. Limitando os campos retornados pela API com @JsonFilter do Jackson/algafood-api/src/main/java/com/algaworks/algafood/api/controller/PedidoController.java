package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.PedidoDTOAssembler;
import com.algaworks.algafood.api.assembler.PedidoDisassembler;
import com.algaworks.algafood.api.assembler.PedidoResumoDTOAssembler;
import com.algaworks.algafood.api.model.PedidoDTO;
import com.algaworks.algafood.api.model.PedidoResumoDTO;
import com.algaworks.algafood.api.model.input.PedidoInput;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.service.PedidoService;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

	@Autowired
	private PedidoService service;

	@Autowired
	private PedidoDTOAssembler assembler;

	@Autowired
	private PedidoDisassembler disassembler;

	@Autowired
	private PedidoResumoDTOAssembler resumoDTOAssembler;

	@GetMapping
	public MappingJacksonValue listar(@RequestParam(required = false) String campos) {
		List<Pedido> pedidos = service.findAll();
		List<PedidoResumoDTO> pedidoResumosDTO = resumoDTOAssembler.toCollectionDTO(pedidos);

		MappingJacksonValue jacksonValue = new MappingJacksonValue(pedidoResumosDTO);

		SimpleFilterProvider filterProvider = new SimpleFilterProvider();
		filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.serializeAll());

		if (StringUtils.isNotBlank(campos)) {
			filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.filterOutAllExcept(campos.split(",")));
		}

		jacksonValue.setFilters(filterProvider);

		return jacksonValue;
	}
	
//	@GetMapping
//	public List<PedidoResumoDTO> listar() {
//		return resumoDTOAssembler.toCollectionDTO(service.findAll());
//	}

	@GetMapping("/{pedidoUuId}")
	public PedidoDTO buscar(@PathVariable String pedidoUuId) {
		return assembler.toDTO(service.findByIdOrNotFound(pedidoUuId));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PedidoDTO emitir(@RequestBody @Valid PedidoInput pedidoInput) {
		Pedido novoPedido = disassembler.toDomainModel(pedidoInput);

		// TODO Usar usuário autenticado
		novoPedido.setCliente(new Usuario());
		novoPedido.getCliente().setId(3L);
			
		return assembler.toDTO(service.emitir(novoPedido));
	}
}
