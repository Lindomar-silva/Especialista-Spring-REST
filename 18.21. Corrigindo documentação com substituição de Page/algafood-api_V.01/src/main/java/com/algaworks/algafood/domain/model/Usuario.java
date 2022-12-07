package com.algaworks.algafood.domain.model;

import java.time.OffsetDateTime;
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

import org.hibernate.annotations.CreationTimestamp;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Usuario {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String nome;
	
	@Column(nullable = false)
    private String email;
	
	@Column(nullable = false)
	private String senha;
	
	@CreationTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private OffsetDateTime dataCadastro;
	
	@ManyToMany
	@JoinTable(name = "usuario_grupo",
			joinColumns = @JoinColumn(name = "usuario_id"),
			inverseJoinColumns = @JoinColumn(name = "grupo_id")
	)
	private Set<Grupo> grupos = new HashSet<>();
	
	public boolean senhaNaoConfere(String senha) {
		return !senha.equals(getSenha());
	}

	public boolean notExistGrupo(Long grupoId) {
		return !existGrupo(grupoId);
	}
	
	public boolean existGrupo(Long grupoId) {
		return getGrupos().stream()
				.filter(grupo -> grupo.getId().equals(grupoId))
				.findFirst()
				.isPresent();
	}

	public boolean adicionarGrupo(Grupo grupo) {
		return getGrupos().add(grupo);
	}

	public boolean removerGrupo(Grupo grupo) {
		return getGrupos().remove(grupo);
	}
}
