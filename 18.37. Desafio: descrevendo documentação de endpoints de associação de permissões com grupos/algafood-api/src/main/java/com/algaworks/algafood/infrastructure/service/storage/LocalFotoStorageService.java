package com.algaworks.algafood.infrastructure.service.storage;

import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;

import com.algaworks.algafood.core.storage.StorageProperties;
import com.algaworks.algafood.domain.service.FotoStorageService;

public class LocalFotoStorageService implements FotoStorageService {

	@Autowired
	private StorageProperties properties;

	@Override
	public void armazenar(NovaFoto novaFoto) {
		try {
			Path arquivoPath = getPathArquivo(novaFoto.getNomeArquivo());
			FileCopyUtils.copy(novaFoto.getInputStream(), Files.newOutputStream(arquivoPath));

		} catch (Exception e) {
			throw new StorageException("Não foi possível armazenar arquivo", e);
		}
	}

	@Override
	public void remover(String nomeArquivo) {
		try {
			Path arquivoPath = getPathArquivo(nomeArquivo);
			Files.deleteIfExists(arquivoPath);

		} catch (Exception e) {
			throw new StorageException("Não foi possível excluir arquivo", e);
		}
	}

	@Override
	public FotoRecuperada recuperar(String nomeArquivo) {
		try {
			Path arquivoPath = getPathArquivo(nomeArquivo);
			
			FotoRecuperada fotoRecuperada = FotoRecuperada.builder()
					.inputStream(Files.newInputStream(arquivoPath))
					.build();
			
			return fotoRecuperada;

		} catch (Exception e) {
			throw new StorageException("Não foi possível recuperar arquivo", e);
		}
	}

	private Path getPathArquivo(String nomeArquivo) {
		return properties.getLocal().getDiretorioFotos().resolve(Path.of(nomeArquivo));
	}
}
