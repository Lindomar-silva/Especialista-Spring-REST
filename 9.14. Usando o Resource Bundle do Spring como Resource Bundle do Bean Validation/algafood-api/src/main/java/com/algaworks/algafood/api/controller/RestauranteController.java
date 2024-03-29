package com.algaworks.algafood.api.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

	@Autowired
	private CadastroRestauranteService restauranteService;

	@GetMapping
	public List<Restaurante> listar() {
		return restauranteService.findAll();
	}
	
	@GetMapping("/{restauranteId}")
	public Restaurante buscar(@PathVariable Long restauranteId) {
		return restauranteService.findByIdOrNotFound(restauranteId);
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Restaurante adicionar(@RequestBody @Valid Restaurante restaurante) {
		return restauranteService.salvar(restaurante);
	}

	@PutMapping("/{restauranteId}")
	public Restaurante atualizar(@PathVariable Long restauranteId, @RequestBody @Valid Restaurante restaurante) {

		Restaurante restauranteAtual = restauranteService.findByIdOrNotFound(restauranteId);

//		BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "formaPagamentos", "endereco", 
//				"dataCadastro",	"produtos");
//		restauranteAtual = restauranteService.salvar(restauranteAtual);
//		return restauranteAtual;

		restaurante.setId(restauranteId);
		restaurante.setDataCadastro(restauranteAtual.getDataCadastro());
//		restaurante.setProdutos(restauranteAtual.getProdutos());
		restaurante.setFormaPagamentos(restauranteAtual.getFormaPagamentos());
		restaurante.setEndereco(restauranteAtual.getEndereco());

		return restauranteService.salvar(restaurante);
	}
	
	@PatchMapping("/{restauranteId}")
	public Restaurante atualizarParcial(@PathVariable Long restauranteId, 
			@RequestBody Map<String, Object> campos) {
		
		Restaurante restauranteAtual = restauranteService.findByIdOrNotFound(restauranteId);

		merge(campos, restauranteAtual);
		return atualizar(restauranteId, restauranteAtual);
	}

	private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

			Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);

				dadosOrigem.forEach((nomeProp, valorProp) -> {
				Field field = ReflectionUtils.findField(Restaurante.class, nomeProp);
				field.setAccessible(true);
	
				Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
	
				System.out.println(nomeProp + ": " + valorProp + " NovoValor: " + novoValor);
	
				ReflectionUtils.setField(field, restauranteDestino, novoValor);
			});
		} catch (IllegalArgumentException e) {
			Throwable rootCause = ExceptionUtils.getRootCause(e);
			throw new HttpMessageNotReadableException(e.getMessage(), rootCause, null);
		}
	}
}
