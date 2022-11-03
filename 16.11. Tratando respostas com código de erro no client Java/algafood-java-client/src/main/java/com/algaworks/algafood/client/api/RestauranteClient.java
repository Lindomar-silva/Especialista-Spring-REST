package com.algaworks.algafood.client.api;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import com.algaworks.algafood.client.model.RestauranteResumoDTO;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RestauranteClient {

	private static final String RESOURCE_PATH = "/restaurantes1";

	private RestTemplate restTemplate;
	private String url;

	public List<RestauranteResumoDTO> listar() {

		try {
			URI resourceUri = URI.create(url + RESOURCE_PATH);
			RestauranteResumoDTO[] restaurantes = restTemplate
					.getForObject(resourceUri, RestauranteResumoDTO[].class);

			return Arrays.asList(restaurantes);

		} catch (RestClientResponseException e) {
			throw new ClientApiException(e.getMessage(), e);
		}
	}
}
