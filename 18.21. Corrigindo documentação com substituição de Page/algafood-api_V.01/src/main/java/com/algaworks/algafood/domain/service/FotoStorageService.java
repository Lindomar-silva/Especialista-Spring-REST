package com.algaworks.algafood.domain.service;

import java.io.InputStream;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

public interface FotoStorageService {

	FotoRecuperada recuperar(String nomeArquivo);

	void armazenar(NovaFoto novaFoto);

	void remover(String nomeArquivo);

	default void incluirRemoverArquivo(String arquivoAntigo, NovaFoto novaFoto) {
		this.armazenar(novaFoto);

		if (arquivoAntigo != null) {
			this.remover(arquivoAntigo);
		}
	}

	default String gerarNomeArquivo(String nomeOriginal) {
		return UUID.randomUUID() + "_" + nomeOriginal;
	}

	@Builder
	@Getter
	class NovaFoto {
		private String nomeArquivo;
		private String contentType;
		private InputStream inputStream;
	}

	@Builder
	@Getter
	class FotoRecuperada {
		private InputStream inputStream;
		private String url;

		public boolean existeUrl() {
			return url != null;
		}

		public boolean existeInputStream() {
			return inputStream != null;
		}
	}
}
