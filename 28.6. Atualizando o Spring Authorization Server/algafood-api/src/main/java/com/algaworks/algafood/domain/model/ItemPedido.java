package com.algaworks.algafood.domain.model;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class ItemPedido {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Integer quantidade;
	private BigDecimal precoUnitario;
	private BigDecimal precoTotal;
	private String observacao;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Produto produto;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Pedido pedido;

	public void calcularPrecoTotal() {

		Integer quantidade = this.getQuantidade();
		BigDecimal precoUnitario = this.getPrecoUnitario();

		if (quantidade == null)
			quantidade = 0;

		if (precoUnitario == null)
			precoUnitario = BigDecimal.ZERO;

		this.setPrecoTotal(precoUnitario.multiply(new BigDecimal(quantidade)));
	}
}
