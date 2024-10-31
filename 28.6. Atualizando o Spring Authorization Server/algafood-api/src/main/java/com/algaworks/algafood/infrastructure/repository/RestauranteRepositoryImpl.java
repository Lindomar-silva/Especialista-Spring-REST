package com.algaworks.algafood.infrastructure.repository;

import static com.algaworks.algafood.infrastructure.repository.spec.RestauranteSpec.comFreteGratis;
import static com.algaworks.algafood.infrastructure.repository.spec.RestauranteSpec.comNomeSemelhante;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepositoryQueries;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {

	@PersistenceContext
	private EntityManager manager;

	@Autowired @Lazy //@Lazy resolve o problema de referencia circular.
	private RestauranteRepository restauranteRepo;

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

	@Override
	public Page<Restaurante> findComFreteGratis(String nome, Pageable pageable) {
		return restauranteRepo.findAll(comFreteGratis().and(comNomeSemelhante(nome)),pageable);
	}
}
