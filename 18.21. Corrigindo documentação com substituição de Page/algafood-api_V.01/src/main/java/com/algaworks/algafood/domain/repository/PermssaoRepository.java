package com.algaworks.algafood.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Permissao;

@Repository
public interface PermssaoRepository extends JpaRepository<Permissao, Long> {

}
