package com.algaworks.algafood.client;

import java.math.BigDecimal;

import org.springframework.web.client.RestTemplate;

import com.algaworks.algafood.client.api.ClientApiException;
import com.algaworks.algafood.client.api.RestauranteClient;
import com.algaworks.algafood.client.model.RestauranteDTO;
import com.algaworks.algafood.client.model.input.CidadeIdInput;
import com.algaworks.algafood.client.model.input.CozinhaIdInput;
import com.algaworks.algafood.client.model.input.EnderecoInput;
import com.algaworks.algafood.client.model.input.RestauranteInput;

public class AdicionaRestauranteMain {

	public static void main(String[] args) {

		try {
			var template = new RestTemplate();
			var client = new RestauranteClient(template, "http://api.algafood.local:8080");

			var cidade = new CidadeIdInput();
			cidade.setId(1L);

			var cozinha = new CozinhaIdInput();
			cozinha.setId(1L);

			var endereco = new EnderecoInput();
			endereco.setCep("13588-000");
			endereco.setLogradouro("Rua 13 de Maio");
			endereco.setNumero("1589");
			endereco.setComplemento("Apartamento 13");
			endereco.setBairro("Jardim América");
			endereco.setCidade(cidade);

			var restauranteInput = new RestauranteInput();
			restauranteInput.setNome("Restaurante Paulista");
			restauranteInput.setTaxaFrete(new BigDecimal(13.5));
			restauranteInput.setCozinha(cozinha);
			restauranteInput.setEndereco(endereco);

			RestauranteDTO resumoDTO = client.adicionar(restauranteInput);
			System.err.println(resumoDTO);

		} catch (ClientApiException e) {
			if (e.getProblem() != null) {
				System.err.println(e.getProblem().getUserMsg());
				
				e.getProblem().getObjects().stream()
						.forEach(obj -> System.err.println("* " + obj.getUserMsg()));

			} else {
				System.err.println("Erro desconhecido!");
				e.printStackTrace();
			}
		}
	}
}
