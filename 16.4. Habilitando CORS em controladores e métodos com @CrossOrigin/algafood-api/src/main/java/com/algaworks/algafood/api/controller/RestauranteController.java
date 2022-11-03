package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.RestauranteDTOAssembler;
import com.algaworks.algafood.api.assembler.RestauranteInputDisassembler;
import com.algaworks.algafood.api.model.RestauranteDTO;
import com.algaworks.algafood.api.model.input.RestauranteInput;
import com.algaworks.algafood.api.model.view.RestauranteView;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;

@CrossOrigin //(origins = { "http://www.algafood.local:8000", "http://127.0.0.1:5500" })
@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

	@Autowired
	private RestauranteDTOAssembler assembler;
	
	@Autowired
	private RestauranteInputDisassembler desassembler;
	
	@Autowired
	private CadastroRestauranteService restauranteService;

	@GetMapping
	public MappingJacksonValue listar(@RequestParam(required = false) String projecao) {
		List<Restaurante> restaurantes = restauranteService.findAll();
		List<RestauranteDTO> restaurantesDTO = assembler.toCollectionDTO(restaurantes);
		
		MappingJacksonValue jacksonValue = new MappingJacksonValue(restaurantesDTO);
		
		jacksonValue.setSerializationView(RestauranteView.Resumo.class);
		
		if ("somente-nome".equals(projecao)) {
			jacksonValue.setSerializationView(RestauranteView.SomenteNome.class);
		} else if ("completo".equals(projecao)) {
			jacksonValue.setSerializationView(null);
		}
		
		return jacksonValue;
	}
	
//	@GetMapping
//	public List<RestauranteDTO> listar() {
//		return assembler.toCollectionDTO(restauranteService.findAll());
//	}
//	
//	@JsonView(RestauranteView.Resumo.class)
//	@GetMapping(params = "projecao=resumo")
//	public List<RestauranteDTO> listarResumo() {
//		return listar();
//	}
	
	@GetMapping("/{restauranteId}")
	public RestauranteDTO buscar(@PathVariable Long restauranteId) {
		Restaurante restaurante = restauranteService.findByIdOrNotFound(restauranteId);

		return assembler.toDTO(restaurante);
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
	public void ativar(@PathVariable Long restauranteId) {
		restauranteService.ativar(restauranteId);
	}
	
	@DeleteMapping("/{restauranteId}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativar(@PathVariable Long restauranteId) {
		restauranteService.inativar(restauranteId);
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
	
//	@PutMapping("/{restauranteId}/fechamento")
//	@ResponseStatus(HttpStatus.NO_CONTENT)
//	public void fecahmento(@PathVariable Long restauranteId) {
//		restauranteService.fechar(restauranteId);
//	}
//
//	@PutMapping("/{restauranteId}/abertura")
//	@ResponseStatus(HttpStatus.NO_CONTENT)
//	public void abertura(@PathVariable Long restauranteId) {
//		restauranteService.abrir(restauranteId);
//	}
	
	@PutMapping("/{restauranteId}/fechamento")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void fechado(@PathVariable Long restauranteId) {
		restauranteService.fechado(restauranteId);
	}

	@PutMapping("/{restauranteId}/abertura")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void aberto(@PathVariable Long restauranteId) {
		restauranteService.aberto(restauranteId);
	}
}
