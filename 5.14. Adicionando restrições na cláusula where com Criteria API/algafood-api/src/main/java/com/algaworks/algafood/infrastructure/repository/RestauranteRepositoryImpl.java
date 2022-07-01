package com.algaworks.algafood.infrastructure.repository;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Restaurante;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<Restaurante> consultarPorNomeFrete(String nome, BigDecimal taxaFreteIni, BigDecimal taxaFreteFim) {

		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Restaurante> criteria = builder.createQuery(Restaurante.class);

		Root<Restaurante> root = criteria.from(Restaurante.class);

		Predicate nomePred = builder.like(root.get("nome"), "%" + nome + "%");
		Predicate taxaFreteIniPred = builder.greaterThanOrEqualTo(root.get("taxaFrete"), taxaFreteIni);
		Predicate taxaFreteFimPred = builder.lessThanOrEqualTo(root.get("taxaFrete"), taxaFreteFim);

		criteria.where(nomePred, taxaFreteIniPred, taxaFreteFimPred);

		TypedQuery<Restaurante> query = manager.createQuery(criteria);

		return query.getResultList();

	}
}
