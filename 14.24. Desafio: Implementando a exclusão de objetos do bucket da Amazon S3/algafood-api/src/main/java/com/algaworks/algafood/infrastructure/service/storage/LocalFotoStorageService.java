package com.algaworks.algafood.infrastructure.service.storage;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;

import com.algaworks.algafood.core.storage.StorageProperties;
import com.algaworks.algafood.domain.service.FotoStorageService;

//@Service
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
	public InputStream recuperar(String nomeArquivo) {
		try {
			Path arquivoPath = getPathArquivo(nomeArquivo);
			return Files.newInputStream(arquivoPath);

		} catch (Exception e) {
			throw new StorageException("Não foi possível recuperar arquivo", e);
		}
	}

	private Path getPathArquivo(String nomeArquivo) {
		return properties.getLocal().getDiretorioFotos().resolve(Path.of(nomeArquivo));
	}
}
