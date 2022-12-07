package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.UsuarioAssembler;
import com.algaworks.algafood.api.model.UsuarioDTO;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/responsaveis")
public class RestauranteUsuarioResponsavelController {

	@Autowired
	private CadastroRestauranteService restauranteService;

	@Autowired
	private UsuarioAssembler usuarioAssembler;

	@GetMapping
	public List<UsuarioDTO> listar(@PathVariable Long restauranteId) {
		Restaurante restaurante = restauranteService.findByIdOrNotFound(restauranteId);
		return usuarioAssembler.toCollectionDTO(restaurante.getResponsaveis());
	}
	
	@PutMapping("/{responsavelId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void associar(@PathVariable Long restauranteId, @PathVariable Long responsavelId) {
		restauranteService.associarResponsavel(restauranteId, responsavelId);
	}
	
	@DeleteMapping("/{responsavelId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void desassociar(@PathVariable Long restauranteId, @PathVariable Long responsavelId) {
		restauranteService.desassociarResponsavel(restauranteId, responsavelId);
	}
}
