package com.algaworks.algafood.infrastructure.repository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.algaworks.algafood.domain.model.Restaurante;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<Restaurante> consultarPorNomeFrete(String nome, 
			BigDecimal taxaFreteIni, BigDecimal taxaFreteFim) {

		var jpql = new StringBuilder();
		var parametros = new HashMap<String, Object>();

		jpql.append("FROM Restaurante WHERE 1 = 1 ");

		if (StringUtils.hasLength(nome)) {
			jpql.append("And nome like :nome ");
			parametros.put("nome", "%" + nome + "%");
		}
		if (taxaFreteIni != null) {
			jpql.append("AND taxaFrete >= :taxaIni ");
			parametros.put("taxaIni", taxaFreteIni);
		}
		if (taxaFreteFim != null) {
			jpql.append("AND taxaFrete <= :taxaFim ");
			parametros.put("taxaFim", taxaFreteFim);
		}

		TypedQuery<Restaurante> query = manager.createQuery(jpql.toString(), Restaurante.class);

		parametros.forEach((key, obj) -> query.setParameter(key, obj));

		return query.getResultList();
	}
}
