package com.algaworks.algafood.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;

@RestController
@RequestMapping("/teste")
public class TesteController {

	@Autowired
	private CozinhaRepository repository;

	@GetMapping("cozinhas/por-nome")
	public List<Cozinha> buscarPorNome(String cozinhaNome) {
		return repository.findByNomeContaining(cozinhaNome);
	}
	
	@GetMapping("cozinhas/unico-por-nome")
	public Optional<Cozinha> buscarPorNome1(String cozinhaNome) {
		return repository.findByNome(cozinhaNome);
	}
}
