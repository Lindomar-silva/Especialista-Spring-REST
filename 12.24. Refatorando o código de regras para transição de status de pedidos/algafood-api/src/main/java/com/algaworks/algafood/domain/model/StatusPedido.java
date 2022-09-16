package com.algaworks.algafood.domain.model;

import java.util.Arrays;
import java.util.List;

public enum StatusPedido {

	CRIADO("Criado"), 
	CONFIRMADO("Confirmado", CRIADO), 
	ENTREGUE("Entregue", CONFIRMADO),
	CANCELADO("Cancelado", CRIADO);

	private String descricao;
	private List<StatusPedido> statusPermitidos;

	StatusPedido(String descricao, StatusPedido... statusPermitidos) {
		this.descricao = descricao;
		this.statusPermitidos = Arrays.asList(statusPermitidos);
	}

	public String getDescricao() {
		return this.descricao;
	}

	public boolean naoPodeAlterarPara(StatusPedido novoStatus) {
		System.err.println("Status Atual: " + this);
		System.err.println("Status Permitdos: " + novoStatus.statusPermitidos);
		System.err.println("Status Novo: " + novoStatus);
		System.err.println("Status Aplicado? " + novoStatus.statusPermitidos.contains(this));
		return !novoStatus.statusPermitidos.contains(this);
	}
}
