package com.algaworks.algafood.client;

import org.springframework.web.client.RestTemplate;

import com.algaworks.algafood.client.api.ClientApiException;
import com.algaworks.algafood.client.api.RestauranteClient;

public class LinstagemRestaurantesMain {

	public static void main(String[] args) {

		try {
			RestTemplate template = new RestTemplate();
			RestauranteClient client = new RestauranteClient(template, "http://api.algafood.local:8080");

			client.listar().stream().forEach(restaurante -> System.out.println(restaurante));

		} catch (ClientApiException e) {
			if (e.getProblem() != null) {
				System.err.println(e.getProblem().getUserMsg());
			} else {
				System.err.println("Erro desconhecido!");
				e.printStackTrace();
			}
		}
	}
}
