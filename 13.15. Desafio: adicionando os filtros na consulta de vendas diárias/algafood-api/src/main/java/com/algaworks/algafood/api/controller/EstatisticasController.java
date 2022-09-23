package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.dto.VendaDiaria;
import com.algaworks.algafood.domain.service.VendaQueryService;

@RestController
@RequestMapping("/estatisticas")
public class EstatisticasController {

	@Autowired
	private VendaQueryService queryService;

	@GetMapping("/vendas-diarias")
	public List<VendaDiaria> consultareVendasDiarias(VendaDiariaFilter filtro) {
		return queryService.consultarVendasDiarias(filtro);
	}
}
