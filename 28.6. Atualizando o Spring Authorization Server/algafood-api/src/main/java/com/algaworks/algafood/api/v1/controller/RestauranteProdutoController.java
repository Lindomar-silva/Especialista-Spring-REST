package com.algaworks.algafood.api.v1.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.assembler.ProdutoAssembler;
import com.algaworks.algafood.api.v1.assembler.ProdutoDisassembler;
import com.algaworks.algafood.api.v1.model.ProdutoDTO;
import com.algaworks.algafood.api.v1.model.input.ProdutoInput;
import com.algaworks.algafood.api.v1.openapi.controller.RestauranteProdutoControllerOpenApi;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import com.algaworks.algafood.domain.service.ProdutoService;

@RestController
@RequestMapping(path = "/v1/restaurantes/{restauranteId}/produtos", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteProdutoController implements RestauranteProdutoControllerOpenApi {

	@Autowired
	private CadastroRestauranteService restauranteService;

	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private ProdutoAssembler assembler;
	
	@Autowired
	private ProdutoDisassembler disassembler;

	@Autowired
	private AlgaLinks algaLinks;
	
	@CheckSecurity.Restaurantes.PodeConsultar
	@Override
	@GetMapping
	public CollectionModel<ProdutoDTO> listar(@PathVariable Long restauranteId,
			@RequestParam(required = false) Boolean incluirInativos) {

		Restaurante restaurante = restauranteService.findByIdOrNotFound(restauranteId);
		List<Produto> produtos = null;

		if (incluirInativos) {
			produtos = produtoService.findAll(restaurante);
		} else {
			produtos = produtoService.findAtivosByRestaurante(restaurante);
		}

		return assembler.toCollectionModel(produtos)
				.add(algaLinks.linkToProdutos(restauranteId));
	}

	@CheckSecurity.Restaurantes.PodeConsultar
	@Override
	@GetMapping("/{produtoId}")
	public ProdutoDTO buscarPorProduto(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
		restauranteService.existByIdOrNotFound(restauranteId);
		Produto produto = produtoService.findByIdOrNotFound(restauranteId, produtoId);

		return assembler.toModel(produto);
	}
	
	@CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ProdutoDTO adicionar(@PathVariable Long restauranteId, @Valid @RequestBody ProdutoInput produtoInput) {
		Restaurante restaurante = restauranteService.findByIdOrNotFound(restauranteId);
		
		Produto produto = disassembler.toDomainModel(produtoInput);
		produto.setRestaurante(restaurante);

		return assembler.toModel(produtoService.salvar(produto));
	}

	@CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
	@Override
	@PutMapping("/{produtoId}")
	public ProdutoDTO alterar(@PathVariable Long restauranteId, @PathVariable Long produtoId, 
			@Valid @RequestBody ProdutoInput produtoInput) {
		
		restauranteService.existByIdOrNotFound(restauranteId);
		Produto produto = produtoService.findByIdOrNotFound(restauranteId, produtoId);
		disassembler.copyCollectionModel(produtoInput, produto);

		return assembler.toModel(produtoService.salvar(produto));
	}
}
