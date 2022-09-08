package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.PedidoDTOAssembler;
import com.algaworks.algafood.api.assembler.PedidoResumoDTOAssembler;
import com.algaworks.algafood.api.model.PedidoDTO;
import com.algaworks.algafood.api.model.PedidoResumoDTO;
import com.algaworks.algafood.domain.service.PedidoService;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

	@Autowired
	private PedidoService service;

	@Autowired
	private PedidoDTOAssembler assembler;
	
	@Autowired
	private PedidoResumoDTOAssembler resumoDTOAssembler;

	@GetMapping
	public List<PedidoResumoDTO> listar() {
		return resumoDTOAssembler.toCollectionDTO(service.findAll());
	}

	@GetMapping("/{pedidoId}")
	public PedidoDTO buscar(@PathVariable Long pedidoId) {
		return assembler.toDTO(service.findByIdOrNotFound(pedidoId));
	}
}
