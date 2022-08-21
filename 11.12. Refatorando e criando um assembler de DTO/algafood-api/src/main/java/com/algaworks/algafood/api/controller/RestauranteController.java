package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.RestauranteDTOAssembler;
import com.algaworks.algafood.api.model.RestauranteDTO;
import com.algaworks.algafood.api.model.input.RestauranteInput;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

//	@Autowired
//	private SmartValidator validator;
	
	@Autowired
	private RestauranteDTOAssembler dtoAssembler;
	
	@Autowired
	private CadastroRestauranteService restauranteService;

	@GetMapping
	public List<RestauranteDTO> listar() {
		return dtoAssembler.toCollectionDTO(restauranteService.findAll());
	}
	
	@GetMapping("/{restauranteId}")
	public RestauranteDTO buscar(@PathVariable Long restauranteId) {
		Restaurante restaurante = restauranteService.findByIdOrNotFound(restauranteId);

		return dtoAssembler.toDTO(restaurante);
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public RestauranteDTO adicionar(@RequestBody @Valid RestauranteInput restauranteInput) {
		return restauranteService.salvar(toDomianModel(restauranteInput));
	}

	@PutMapping("/{restauranteId}")
	public RestauranteDTO atualizar(@PathVariable Long restauranteId, @RequestBody @Valid RestauranteInput restauranteInput) {

		Restaurante restauranteAtual = restauranteService.findByIdOrNotFound(restauranteId);

//		BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "formaPagamentos", "endereco", 
//				"dataCadastro",	"produtos");
//		restauranteAtual = restauranteService.salvar(restauranteAtual);
//		return restauranteAtual;

		Restaurante restaurante = toDomianModel(restauranteInput);
		
		restaurante.setId(restauranteId);
		restaurante.setDataCadastro(restauranteAtual.getDataCadastro());
//		restaurante.setProdutos(restauranteAtual.getProdutos());
		restaurante.setFormaPagamentos(restauranteAtual.getFormaPagamentos());
		restaurante.setEndereco(restauranteAtual.getEndereco());

		return restauranteService.salvar(restaurante);
	}
	
//	@PatchMapping("/{restauranteId}")
//	public Restaurante atualizarParcial(@PathVariable Long restauranteId, 
//			@RequestBody Map<String, Object> campos) {
//		
//		Restaurante restauranteAtual = restauranteService.findByIdOrNotFound(restauranteId);
//
//		merge(campos, restauranteAtual);
//		validate(restauranteAtual, "restaurante");
//		
//		return atualizar(restauranteId, restauranteAtual);
//	}
//
//	private void validate(Restaurante restaurante, String objectName) {
//		BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(restaurante, objectName);
//		validator.validate(restaurante, bindingResult);
//
//		if (bindingResult.hasErrors()) {
//			throw new ValidacaoException(bindingResult);
//		}
//
//	}
//
//	private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino) {
//		try {
//			ObjectMapper objectMapper = new ObjectMapper();
//			objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
//			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
//
//			Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);
//
//				dadosOrigem.forEach((nomeProp, valorProp) -> {
//				Field field = ReflectionUtils.findField(Restaurante.class, nomeProp);
//				field.setAccessible(true);
//	
//				Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
//	
//				System.out.println(nomeProp + ": " + valorProp + " NovoValor: " + novoValor);
//	
//				ReflectionUtils.setField(field, restauranteDestino, novoValor);
//			});
//		} catch (IllegalArgumentException e) {
//			Throwable rootCause = ExceptionUtils.getRootCause(e);
//			throw new HttpMessageNotReadableException(e.getMessage(), rootCause, null);
//		}
//	}
	
	private Restaurante toDomianModel(RestauranteInput restauranteInput) {
		Restaurante restaurante = new Restaurante();
		restaurante.setNome(restauranteInput.getNome());
		restaurante.setTaxaFrete(restauranteInput.getTaxaFrete());

		Cozinha cozinha = new Cozinha();
		cozinha.setId(restauranteInput.getCozinha().getId());

		restaurante.setCozinha(cozinha);

		return restaurante;
	}
}
