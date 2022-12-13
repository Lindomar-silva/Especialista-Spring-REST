package com.algaworks.algafood.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>, ProdutoRepositoryQueries {

//	@Query("FROM Produto p WHERE p.id = :produtoId AND p.restaurante.id = :restauranteId")
	Optional<Produto> findByRestauranteIdAndId(Long restauranteId, Long produtoId);

	List<Produto> findAllByRestaurante(Restaurante restaurante);
	List<Produto> findByAtivoTrueAndRestaurante(Restaurante restaurante);

	@Query("SELECT f FROM FotoProduto f "
			+ "WHERE f.produto.restaurante.id = :restauranteId AND f.produto.id = :produtoId")
	Optional<FotoProduto> findFotoById(Long restauranteId, Long produtoId);

}
