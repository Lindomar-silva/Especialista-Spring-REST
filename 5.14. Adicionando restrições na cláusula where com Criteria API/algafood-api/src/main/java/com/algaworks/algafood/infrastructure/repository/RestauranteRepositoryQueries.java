package com.algaworks.algafood.infrastructure.repository;

import java.math.BigDecimal;
import java.util.List;

import com.algaworks.algafood.domain.model.Restaurante;

public interface RestauranteRepositoryQueries {

	List<Restaurante> consultarPorNomeFrete(String nome, BigDecimal taxaFreteIni, BigDecimal taxaFreteFim);

}