package com.algaworks.algafood.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

//	@Query("FROM Produto p WHERE p.id = :produtoId AND p.restaurante.id = :restauranteId")
	Optional<Produto> findByRestauranteIdAndId(Long restauranteId, Long produtoId);
}
