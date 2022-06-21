package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

	@Autowired
	private RestauranteRepository repository;

	@Autowired
	private CadastroRestauranteService service;

	@GetMapping
	public List<Restaurante> listar() {
		return repository.listar();
	}

	@GetMapping("/{restauranteId}")
	public ResponseEntity<Restaurante> buscar(@PathVariable Long restauranteId) {
		Restaurante restaurante = repository.buscar(restauranteId);

		if (restaurante != null) {
			return ResponseEntity.ok(restaurante);
		}

		return ResponseEntity.notFound().build();
	}

	@PostMapping
	public ResponseEntity<?> adicionar(@RequestBody Restaurante restaurante) {
		try {

			restaurante = service.salvar(restaurante);
			return ResponseEntity.status(HttpStatus.CREATED).body(restaurante);

		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PutMapping("/{restauranteId}")
	public ResponseEntity<?> atualizar(@PathVariable Long restauranteId, @RequestBody Restaurante restaurante) {
		try {

			if (repository.buscar(restauranteId) != null) {
				restaurante.setId(restauranteId);
				restaurante = service.salvar(restaurante);
				return ResponseEntity.ok(restaurante);
			}

			return ResponseEntity.notFound().build();

		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
