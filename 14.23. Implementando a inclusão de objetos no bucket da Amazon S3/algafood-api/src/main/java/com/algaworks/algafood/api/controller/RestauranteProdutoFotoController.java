package com.algaworks.algafood.api.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.algaworks.algafood.api.assembler.FotoProdutoAssembler;
import com.algaworks.algafood.api.model.FotoProdutoDTO;
import com.algaworks.algafood.api.model.input.FotoProdutoInput;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.service.CatalogoFotoProdutoService;
import com.algaworks.algafood.domain.service.FotoStorageService;
import com.algaworks.algafood.domain.service.ProdutoService;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoController {

	@Autowired
	private CatalogoFotoProdutoService service;

	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private FotoProdutoAssembler assembler;
	
	@Autowired
	private FotoStorageService storageService;

	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public FotoProdutoDTO atualizaFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId,
			@Valid FotoProdutoInput fotoProdutoInput) throws IOException {

		MultipartFile arquivo = fotoProdutoInput.getArquivo();

		Produto produto = produtoService.findByIdOrNotFound(restauranteId, produtoId);
		FotoProduto foto = new FotoProduto();

		foto.setProduto(produto);
		foto.setDescricao(fotoProdutoInput.getDescricao());
		foto.setContentType(arquivo.getContentType());
		foto.setTamanho(arquivo.getSize());
		foto.setNomeArquivo(arquivo.getOriginalFilename());

		FotoProduto fotoSalva = service.salvar(foto, arquivo.getInputStream());

		return assembler.toDTO(fotoSalva);
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public FotoProdutoDTO buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
		FotoProduto fotoProduto = service.findByIdOrNotFound(restauranteId, produtoId);
		return assembler.toDTO(fotoProduto);
	}
	
	@GetMapping
	public ResponseEntity<InputStreamResource> exibirFoto(@RequestHeader(name = "accept") String acceptHeader,
			@PathVariable Long restauranteId, @PathVariable Long produtoId) throws HttpMediaTypeNotAcceptableException {

		try {
			FotoProduto fotoProduto = service.findByIdOrNotFound(restauranteId, produtoId);

			MediaType mediaTypeFoto = MediaType.parseMediaType(fotoProduto.getContentType());
			List<MediaType> mediaTypesHeader = MediaType.parseMediaTypes(acceptHeader);
			
			isAcceptMediaType(mediaTypeFoto, mediaTypesHeader);
			
			InputStream inputStream = storageService.recuperar(fotoProduto.getNomeArquivo());
			
			return ResponseEntity.ok()
					.contentType(mediaTypeFoto)
					.body(new InputStreamResource(inputStream));
			
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping()
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
		service.remover(restauranteId, produtoId);
	}
	
	private void isAcceptMediaType(MediaType mediaTypeFoto, List<MediaType> mediaTypesHeader)
			throws HttpMediaTypeNotAcceptableException {

		boolean accept = mediaTypesHeader.stream().anyMatch(x -> x.isCompatibleWith(mediaTypeFoto));

		if (!accept) {
			throw new HttpMediaTypeNotAcceptableException(mediaTypesHeader);
		}
	}

}
