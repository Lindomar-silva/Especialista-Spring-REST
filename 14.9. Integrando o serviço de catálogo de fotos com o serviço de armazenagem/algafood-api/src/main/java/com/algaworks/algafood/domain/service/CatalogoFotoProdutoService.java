package com.algaworks.algafood.domain.service;

import java.io.InputStream;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import com.algaworks.algafood.domain.service.FotoStorageService.NovaFoto;

@Service
public class CatalogoFotoProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private FotoStorageService storageService;

	@Transactional
	public FotoProduto salvar(FotoProduto foto, InputStream inputStream) {

		String nomeNovoArquivo = storageService.gerarNomeArquivo(foto.getNomeArquivo());

		Optional<FotoProduto> existeFoto = produtoRepository.findFotoById(
				foto.getRestauranteId(), foto.getProduto().getId());

		if (existeFoto.isPresent()) {
//			foto.setId(foto.getProduto().getId());
			produtoRepository.delete(existeFoto.get());
		}
		
		foto.setNomeArquivo(nomeNovoArquivo);
		foto = produtoRepository.save(foto);
		produtoRepository.flush();
		
		NovaFoto novaFoto =NovaFoto.builder()
				.nomeArquivo(foto.getNomeArquivo())
				.inputStream(inputStream)
				.build();
		
		storageService.armazenar(novaFoto);
		
		return foto; 
	}
}
