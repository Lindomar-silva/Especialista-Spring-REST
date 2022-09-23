package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.filter.PedidoFilter;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import com.algaworks.algafood.domain.service.PedidoService;
import com.algaworks.algafood.domain.service.UsuarioService;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private CadastroRestauranteService restauranteService;
	
	@Autowired
	private PedidoService service;

	@Autowired
	private PedidoDTOAssembler assembler;

	@Autowired
	private PedidoDisassembler disassembler;

	@Autowired
	private PedidoResumoDTOAssembler resumoDTOAssembler;
	
	@GetMapping
	public List<PedidoResumoDTO> pesquisar(PedidoFilter filtro) {
		List<Pedido> pedidos = service.findAll(filtro);
				
		if (pedidos.isEmpty()) {
			usuarioService.findByIdOrNotFound(filtro.getClienteId());
			restauranteService.findByIdOrNotFound(filtro.getRestauranteId());
		}

		return resumoDTOAssembler.toCollectionDTO(pedidos);
	}

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
