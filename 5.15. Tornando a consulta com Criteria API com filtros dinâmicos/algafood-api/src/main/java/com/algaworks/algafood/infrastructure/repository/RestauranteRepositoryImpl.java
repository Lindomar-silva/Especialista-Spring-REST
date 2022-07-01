package com.algaworks.algafood.infrastructure.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.algaworks.algafood.domain.model.Restaurante;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<Restaurante> consultarPorNomeFrete(String nome, BigDecimal taxaFreteIni, BigDecimal taxaFreteFim) {

		var builder = manager.getCriteriaBuilder();
		var criteria = builder.createQuery(Restaurante.class);

		var root = criteria.from(Restaurante.class);

		var predicates = new ArrayList<Predicate>();

		if (StringUtils.hasLength(nome)) {
			predicates.add(builder.like(root.get("nome"), "%" + nome + "%"));
		}
		if (taxaFreteIni != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get("taxaFrete"), taxaFreteIni));
		}
		if (taxaFreteFim != null) {
			predicates.add(builder.lessThanOrEqualTo(root.get("taxaFrete"), taxaFreteFim));
		}

		criteria.where(predicates.toArray(new Predicate[0]));

		var query = manager.createQuery(criteria);

		return query.getResultList();

	}
}
