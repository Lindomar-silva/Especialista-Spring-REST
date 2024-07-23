package com.algaworks.algafood.api.v1.openapi.controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.multipart.MultipartFile;

import com.algaworks.algafood.api.v1.model.FotoProdutoDTO;
import com.algaworks.algafood.api.v1.model.input.FotoProdutoInput;

public interface RestauranteProdutoFotoControllerOpenApi {

	FotoProdutoDTO atualizaFoto(Long restauranteId, Long produtoId, FotoProdutoInput fotoProdutoInput,
			MultipartFile arquivo) throws IOException;

	ResponseEntity<?> buscar(Long restauranteId, Long produtoId, String acceptHeader)
			throws HttpMediaTypeNotAcceptableException;

//	ResponseEntity<?> exibirFoto(String acceptHeader, Long restauranteId, Long produtoId) 
//			throws HttpMediaTypeNotAcceptableException;

	void remover(Long restauranteId, Long produtoId);

}
