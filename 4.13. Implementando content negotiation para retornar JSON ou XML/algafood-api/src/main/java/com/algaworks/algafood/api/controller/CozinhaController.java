package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;

@RestController
@RequestMapping(path = "/cozinhas") // , produces = MediaType.APPLICATION_XML_VALUE)
public class CozinhaController {

	@Autowired
	private CozinhaRepository repository;

	@GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
	public List<Cozinha> listar1() {
		return repository.listar();
	}

//	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_ATOM_XML_VALUE })
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Cozinha> listar2() {
		return repository.listar();
	}
}
