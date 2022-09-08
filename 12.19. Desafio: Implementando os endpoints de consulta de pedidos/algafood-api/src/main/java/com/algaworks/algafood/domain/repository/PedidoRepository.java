package com.algaworks.algafood.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Pedido;

@Repository
public interface PedidoRepository extends CustomJpaRepository<Pedido, Long> {

	// Resolvendo o problema N+1
	@Query("FROM Pedido p JOIN FETCH p.cliente JOIN FETCH p.restaurante r "
			+ "JOIN FETCH r.cozinha JOIN FETCH p.formaPagamento "
			+ "LEFT JOIN FETCH p.itens i LEFT JOIN FETCH i.produto "
			+ "LEFT JOIN FETCH p.enderecoEntrega.cidade c LEFT JOIN FETCH c.estado ")
	List<Pedido> findAll();
}
