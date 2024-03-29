package com.algaworks.algafood.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.repository.ProdutoRepository;

@Service
public class CatalogoFotoProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;

	@Transactional
	public FotoProduto salvar(FotoProduto foto) {

		Optional<FotoProduto> existeFoto = produtoRepository.findFotoById(
				foto.getRestauranteId(), foto.getProduto().getId());

		if (existeFoto.isPresent()) {
//			foto.setId(foto.getProduto().getId());
			produtoRepository.delete(existeFoto.get());
		}
		
		return produtoRepository.save(foto);
	}
}
