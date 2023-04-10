package com.algaworks.algafood.api.v2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.v2.AlgaLinksV2;

@RestController
@RequestMapping(path = "v2", produces = MediaType.APPLICATION_JSON_VALUE)
public class RootEntryPointControllerV2 {

	@Autowired
	private AlgaLinksV2 algaLinks;

	@GetMapping
	public RootEntryPointModel root() {
		var rootEntryPointModel = new RootEntryPointModel();

		rootEntryPointModel.add(algaLinks.linkToCidades("cidades"));
		rootEntryPointModel.add(algaLinks.linkToCozinhas("cozinhas"));
		
		return rootEntryPointModel;
	}

	private static class RootEntryPointModel extends RepresentationModel<RootEntryPointModel> {

	}
}
