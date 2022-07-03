package com.algaworks.algafood.api.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.FetchType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

	@Autowired
	private RestauranteRepository restauranteRepo;

	@Autowired
	private CadastroRestauranteService restauranteService;

	@GetMapping
	public List<Restaurante> listar() {
		return restauranteRepo.findAll();
	}

	// TESTE -> fetch = FetchType.LAZY
//	@GetMapping
//	public List<Restaurante> listar() {
//		List<Restaurante> restaurantes= restauranteRepo.findAll();
//		
//		System.out.println("O nome da cozinha é: ");
//		System.out.println(restaurantes.get(0).getCozinha().getNome());
//		
//		return restaurantes;
//	}
	
	@GetMapping("/{restauranteId}")
	public ResponseEntity<Restaurante> buscar(@PathVariable Long restauranteId) {
		Optional<Restaurante> restaurante = restauranteRepo.findById(restauranteId);

		if (restaurante.isPresent()) {
			return ResponseEntity.ok(restaurante.get());
		}

		return ResponseEntity.notFound().build();
	}

	@PostMapping
	public ResponseEntity<?> adicionar(@RequestBody Restaurante restaurante) {
		try {

			restaurante = restauranteService.salvar(restaurante);
			return ResponseEntity.status(HttpStatus.CREATED).body(restaurante);

		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PutMapping("/{restauranteId}")
	public ResponseEntity<?> atualizar(@PathVariable Long restauranteId, @RequestBody Restaurante restaurante) {
		try {

			Restaurante restauranteAtual = restauranteRepo.findById(restauranteId).orElse(null);

			if (restauranteAtual != null) {
//				BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "formaPagamentos", "endereco",
//						"dataCadastro", "produtos");
//				restauranteAtual = restauranteService.salvar(restauranteAtual);
//				return ResponseEntity.ok(restauranteAtual);
				
				restaurante.setId(restauranteId);
				restaurante.setDataCadastro(restauranteAtual.getDataCadastro());
//				restaurante.setProdutos(restauranteAtual.getProdutos());
				restaurante.setFormaPagamentos(restauranteAtual.getFormaPagamentos());
				restaurante.setEndereco(restauranteAtual.getEndereco());
				restaurante = restauranteService.salvar(restaurante);

				return ResponseEntity.ok(restaurante);
			}

			return ResponseEntity.notFound().build();
			
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PatchMapping("/{restaranteId}")
	public ResponseEntity<?> atualizarParcial(@PathVariable Long restaranteId, 
			@RequestBody Map<String, Object> campos) {
		Restaurante restauranteAtual = restauranteRepo.findById(restaranteId).orElse(null);
		
		if (restauranteAtual == null) {
			return ResponseEntity.notFound().build();
		}
		merge(campos, restauranteAtual);
		
		return atualizar(restaranteId, restauranteAtual);
	}

	private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino) {
		ObjectMapper objectMapper = new ObjectMapper();
		Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);

		System.out.println(restauranteOrigem);

		dadosOrigem.forEach((nomeProp, valorProp) -> {
			Field field = ReflectionUtils.findField(Restaurante.class, nomeProp);
			field.setAccessible(true);

			Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);

			System.out.println(nomeProp + ": " + valorProp + " NovoValor: " + novoValor);

			ReflectionUtils.setField(field, restauranteDestino, novoValor);
		});
	}
}
