package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.ProdutoAssembler;
import com.algaworks.algafood.api.assembler.ProdutoDisassembler;
import com.algaworks.algafood.api.model.ProdutoDTO;
import com.algaworks.algafood.api.model.input.ProdutoInput;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import com.algaworks.algafood.domain.service.ProdutoService;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoController {

	@Autowired
	private CadastroRestauranteService restauranteService;

	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private ProdutoAssembler assembler;
	
	@Autowired
	private ProdutoDisassembler disassembler;

	@GetMapping
	public List<ProdutoDTO> listar(@PathVariable Long restauranteId,
			@RequestParam(required = false) boolean incluirInativos) {

		Restaurante restaurante = restauranteService.findByIdOrNotFound(restauranteId);
		List<Produto> produtos = null;

		if (incluirInativos) {
			produtos = produtoService.findAll(restaurante);
		} else {
			produtos = produtoService.findAtivosByRestaurante(restaurante);
		}

		return assembler.toCollectionDTO(produtos);
	}

	@GetMapping("/{produtoId}")
	public ProdutoDTO buscarPorProduto(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
		restauranteService.existByIdOrNotFound(restauranteId);
		Produto produto = produtoService.findByIdOrNotFound(restauranteId, produtoId);

		return assembler.toDTO(produto);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ProdutoDTO adicionar(@PathVariable Long restauranteId, @Valid @RequestBody ProdutoInput produtoInput) {
		Restaurante restaurante = restauranteService.findByIdOrNotFound(restauranteId);
		
		Produto produto = disassembler.toDomainModel(produtoInput);
		produto.setRestaurante(restaurante);

		return assembler.toDTO(produtoService.salvar(produto));
	}

	@PutMapping("/{produtoId}")
	public ProdutoDTO alterar(@PathVariable Long restauranteId, @PathVariable Long produtoId, 
			@Valid @RequestBody ProdutoInput produtoInput) {
		
		restauranteService.existByIdOrNotFound(restauranteId);
		Produto produto = produtoService.findByIdOrNotFound(restauranteId, produtoId);
		disassembler.copyCollectionModel(produtoInput, produto);

		return assembler.toDTO(produtoService.salvar(produto));
	}
}
