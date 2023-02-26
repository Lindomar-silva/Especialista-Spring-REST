package com.algaworks.algafood.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.assembler.UsuarioAssembler;
import com.algaworks.algafood.api.model.UsuarioDTO;
import com.algaworks.algafood.api.openapi.controller.RestauranteUsuarioResponsavelControllerOpenApi;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping(path = "/restaurantes/{restauranteId}/responsaveis", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteUsuarioResponsavelController implements RestauranteUsuarioResponsavelControllerOpenApi {

	@Autowired
	private CadastroRestauranteService restauranteService;

	@Autowired
	private UsuarioAssembler usuarioAssembler;

	@Autowired
	private AlgaLinks algaLinks;
	
	@GetMapping
	public CollectionModel<UsuarioDTO> listar(@PathVariable Long restauranteId) {
		Restaurante restaurante = restauranteService.findByIdOrNotFound(restauranteId);
		
		CollectionModel<UsuarioDTO> usuariosDTO = usuarioAssembler.toCollectionModel(restaurante.getResponsaveis())
				.removeLinks()
				.add(algaLinks.linkToResponsaveisRestaurante(restauranteId))
				.add(algaLinks.linkToResponsaveisRestauranteAssociar(restauranteId, "associar"));
				
		usuariosDTO.getContent().forEach(usuarioDTO -> {
			usuarioDTO.add(algaLinks.linkToResponsaveisRestauranteDesassociar(
					restauranteId, usuarioDTO.getId(), "desassociar"));
		});
		
		return usuariosDTO;		
	}
	
	@PutMapping("/{responsavelId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> associar(@PathVariable Long restauranteId, @PathVariable Long responsavelId) {
		restauranteService.associarResponsavel(restauranteId, responsavelId);
		
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{responsavelId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> desassociar(@PathVariable Long restauranteId, @PathVariable Long responsavelId) {
		restauranteService.desassociarResponsavel(restauranteId, responsavelId);
		
		return ResponseEntity.noContent().build();
	}
}
