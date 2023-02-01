package com.algaworks.algafood.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Usuario;

@Repository
public interface UsuarioRepository extends CustomJpaRepository<Usuario, Long> {

	Optional<Usuario> findTop1ByEmail(String email);

	@Query("SELECT COUNT(u) > 0 FROM Usuario u WHERE u.email = :email AND (:id IS NULL OR u.id <> :id)")
	boolean existsByEmailAndIdNot(String email, Long id);
}
