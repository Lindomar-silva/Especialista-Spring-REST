package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.ProdutoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.ProdutoRepository;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository repository;
	
	public List<Produto> findAll(Restaurante restaurante) {
		return repository.findAllByRestaurante(restaurante);
	}
	
	public Produto findByIdOrNotFound(Long restauranteId, Long produtoId) {
		return repository.findByRestauranteIdAndId(restauranteId, produtoId)
				.orElseThrow(() -> new ProdutoNaoEncontradoException(produtoId, restauranteId));
	}

	public List<Produto> findAtivosByRestaurante(Restaurante restaurante) {
		return repository.findByAtivoTrueAndRestaurante(restaurante);
	}

	@Transactional
	public Produto salvar(Produto produto) {
		return repository.save(produto);
	}
}
