package com.algaworks.algafood.domain.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Grupo {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String nome;

	@ManyToMany
	@JoinTable(name = "grupo_permissao",
			joinColumns = @JoinColumn(name = "grupo_id"),
			inverseJoinColumns = @JoinColumn(name = "permissao_id")
	)
	private Set<Permissao> permissaos = new HashSet<>();

	public void adicionarPermissao(Permissao permissao) {
		getPermissaos().add(permissao);
	}

	public void removerPermissao(Permissao permissao) {
		getPermissaos().remove(permissao);
	}

	public boolean notExistPermissao(Long permissaoId) {
		return !existPermissao(permissaoId);
	}
	
	public boolean existPermissao(Long permissaoId) {
		return getPermissaos().stream()
				.filter(permissao -> permissao.getId().equals(permissaoId))
				.findFirst()
				.isPresent();
	}

}
