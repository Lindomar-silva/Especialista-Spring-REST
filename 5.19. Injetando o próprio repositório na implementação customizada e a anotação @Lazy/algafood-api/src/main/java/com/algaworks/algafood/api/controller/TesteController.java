package com.algaworks.algafood.api.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

@RestController
@RequestMapping("/teste")
public class TesteController {

	@Autowired
	private CozinhaRepository cozinhaRepo;

	@Autowired
	private RestauranteRepository restauranteRep;

	@GetMapping("/cozinhas/por-nome")
	public List<Cozinha> buscarPorNome(String cozinhaNome) {
		return cozinhaRepo.findByNomeContaining(cozinhaNome);
	}

	@GetMapping("/cozinhas/unico-por-nome")
	public Optional<Cozinha> buscarPorNome1(String cozinhaNome) {
		return cozinhaRepo.findByNome(cozinhaNome);
	}

	@GetMapping("/restaurantes/por-taxa-frete")
	public List<Restaurante> restaurantePorTaxaFrete(BigDecimal taxaIni, BigDecimal taxaFim) {
		return restauranteRep.findByTaxaFreteBetween(taxaIni, taxaFim);
	}

	@GetMapping("/restaurantes/por-nome")
	public List<Restaurante> restaurantePorNome(String nome, Long conzinhaId) {
		return restauranteRep.consultarPorNome(nome, conzinhaId);
	}

	@GetMapping("/restaurantes/first-por-nome")
	public Optional<Restaurante> primeiroNome(String nome) {
		return restauranteRep.findFirstRestauranteByNomeContaining(nome);
	}

	@GetMapping("/restaurantes/top2-por-nome")
	public List<Restaurante> top2PorNome(String nome) {
		return restauranteRep.findTop2ByNomeContaining(nome);
	}

	@GetMapping("/restaurantes/por-nome-e-frete")
	public List<Restaurante> restaurantePorNomeFrete(String nome, BigDecimal taxaIni, BigDecimal taxaFim) {
		return restauranteRep.consultarPorNomeFrete(nome, taxaIni, taxaFim);
	}

	@GetMapping("/cozinhas/exist-nome")
	public boolean existNome(String nome) {
		return cozinhaRepo.existsByNome(nome);
	}

	@GetMapping("/restaurantes/count-por-cozinga")
	public int top2PorNome(Long cozinhaId) {
		return restauranteRep.countByCozinhaId(cozinhaId);
	}

	@GetMapping("/restaurantes/com-frete-gratis")
	public Page<Restaurante> restauranteFreteGratis(String nome, Pageable pageable) {
		return restauranteRep.findComFreteGratis(nome, pageable);
	}
}
