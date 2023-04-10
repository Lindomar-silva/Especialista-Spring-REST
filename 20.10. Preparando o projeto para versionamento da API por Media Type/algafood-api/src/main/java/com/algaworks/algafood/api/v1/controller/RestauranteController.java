package com.algaworks.algafood.api.v1.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.v1.assembler.RestauranteApenasNomeDTOAssembler;
import com.algaworks.algafood.api.v1.assembler.RestauranteBasicoDTOAssembler;
import com.algaworks.algafood.api.v1.assembler.RestauranteDTOAssembler;
import com.algaworks.algafood.api.v1.assembler.RestauranteInputDisassembler;
import com.algaworks.algafood.api.v1.model.RestauranteApenasNomeDTO;
import com.algaworks.algafood.api.v1.model.RestauranteBasicoDTO;
import com.algaworks.algafood.api.v1.model.RestauranteDTO;
import com.algaworks.algafood.api.v1.model.input.RestauranteInput;
import com.algaworks.algafood.api.v1.openapi.controller.RestauranteControllerOpenApi;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping(path = "/restaurantes", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteController implements RestauranteControllerOpenApi {

	@Autowired
	private RestauranteDTOAssembler assembler;
	
	@Autowired
	private RestauranteInputDisassembler desassembler;
	
	@Autowired
	private CadastroRestauranteService restauranteService;
	
	@Autowired
	private RestauranteBasicoDTOAssembler restauranteBasicoDTOAssembler;

	@Autowired
	private RestauranteApenasNomeDTOAssembler restauranteApenasNomeDTOAssembler;
	
	@GetMapping
	public CollectionModel<RestauranteBasicoDTO> listar() {
		return restauranteBasicoDTOAssembler.toCollectionModel(restauranteService.findAll());
	}
	
//	@JsonView(RestauranteView.Resumo.class)
	@GetMapping(params = "projecao=resumo")
	public CollectionModel<RestauranteApenasNomeDTO> listarResumo() {
		return restauranteApenasNomeDTOAssembler.toCollectionModel(restauranteService.findAll());
	}
	
	@GetMapping("/{restauranteId}")
	public RestauranteDTO buscar(@PathVariable Long restauranteId) {
		Restaurante restaurante = restauranteService.findByIdOrNotFound(restauranteId);

		return assembler.toModel(restaurante);
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public RestauranteDTO adicionar(@RequestBody @Valid RestauranteInput restauranteInput) {
		return restauranteService.salvar(desassembler.toDomianModel(restauranteInput));
	}

	@PutMapping("/{restauranteId}")
	public RestauranteDTO atualizar(@PathVariable Long restauranteId, 
			@RequestBody @Valid RestauranteInput restauranteInput) {
		
		Restaurante restauranteAtual = restauranteService.findByIdOrNotFound(restauranteId);

		desassembler.copyToDomainModel(restauranteInput, restauranteAtual);
		
		return restauranteService.salvar(restauranteAtual);
	}
	
	@PutMapping("/{restauranteId}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> ativar(@PathVariable Long restauranteId) {
		restauranteService.ativar(restauranteId);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{restauranteId}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> inativar(@PathVariable Long restauranteId) {
		restauranteService.inativar(restauranteId);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/ativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativarMultiplos(@RequestBody List<Long> restauranteIds) {
		restauranteService.ativarMultiplos(restauranteIds);
	}

	@DeleteMapping("/ativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativarMultiplos(@RequestBody List<Long> restauranteIds) {
		restauranteService.inativarMultiplos(restauranteIds);
	}
	
	@PutMapping("/{restauranteId}/fechamento")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> fechado(@PathVariable Long restauranteId) {
		restauranteService.fechado(restauranteId);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{restauranteId}/abertura")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> aberto(@PathVariable Long restauranteId) {
		restauranteService.aberto(restauranteId);
		return ResponseEntity.noContent().build();
	}
}
