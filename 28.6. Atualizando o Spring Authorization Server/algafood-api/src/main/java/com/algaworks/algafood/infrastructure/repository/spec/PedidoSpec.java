package com.algaworks.algafood.infrastructure.repository.spec;

import java.util.ArrayList;

import jakarta.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.algaworks.algafood.domain.filter.PedidoFilter;
import com.algaworks.algafood.domain.model.Pedido;

public class PedidoSpec {

	public static Specification<Pedido> usandoFiltro(PedidoFilter filtro) {
		return (root, query, builder) -> {
			
			// Resolvendo erro "query specified join fetching..."
			// Verifica se o retorno é do tipo Pedido e faz um FETCH
			// Se o retorno for um numerico no caso um (COUNT) nnão faz FECTH
			if (Pedido.class.equals(query.getResultType())) {
				root.fetch("restaurante").fetch("cozinha");
				root.fetch("cliente");
			}
			
			var predicates = new ArrayList<Predicate>();

			if (filtro.getClienteId() != null) {
				predicates.add(builder.equal(root.get("cliente").get("id"), filtro.getClienteId()));
			}

			if (filtro.getRestauranteId() != null) {
				predicates.add(builder.equal(root.get("restaurante").get("id"), filtro.getRestauranteId()));
			}

			if (filtro.getDataCriacaoInicio() != null) {
				predicates.add(builder.greaterThanOrEqualTo(root.get("dataCriacao"), filtro.getDataCriacaoInicio()));
			}

			if (filtro.getDataCriacaoFim() != null) {
				predicates.add(builder.lessThanOrEqualTo(root.get("dataCriacao"), filtro.getDataCriacaoFim()));
			}

			return builder.and(predicates.toArray(new Predicate[0]));
		};
	}

}
