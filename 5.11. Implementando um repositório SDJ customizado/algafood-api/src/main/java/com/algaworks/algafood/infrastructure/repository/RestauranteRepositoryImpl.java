package com.algaworks.algafood.infrastructure.repository;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Restaurante;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<Restaurante> consultarPorNomeFrete(String nome, 
			BigDecimal taxaFreteIni, BigDecimal taxaFreteFim){
		
		var jpql = "FROM Restaurante WHERE nome like :nome AND taxaFrete BETWEEN :taxaIni AND :taxaFim";
		
		return manager.createQuery(jpql, Restaurante.class)
				.setParameter("nome", "%" + nome + "%")
				.setParameter("taxaIni", taxaFreteIni)
				.setParameter("taxaFim", taxaFreteFim)
				.getResultList();
	}
}
