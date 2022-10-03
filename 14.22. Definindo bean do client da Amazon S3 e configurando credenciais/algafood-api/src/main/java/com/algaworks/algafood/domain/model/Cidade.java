package com.algaworks.algafood.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Cidade {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

//	@NotBlank(message = "Nome obrigatório!")
	@Column(nullable = false)
	private String nome;

//	@Valid
//	@ConvertGroup(from = Default.class, to = Groups.EstadoId.class)
//	@NotNull
	@ManyToOne
	@JoinColumn(nullable = false)
	private Estado estado;
}
