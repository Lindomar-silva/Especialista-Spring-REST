package com.algaworks.algafood.infrastructure.service.storage;

import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;

import com.algaworks.algafood.core.storage.StorageProperties;
import com.algaworks.algafood.domain.service.FotoStorageService;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

public class S3FotoStorageService implements FotoStorageService {

	@Autowired
	private AmazonS3 amazonS3;

	@Autowired
	private StorageProperties properties;

	@Override
	public FotoRecuperada recuperar(String nomeArquivo) {
		String caminhoArquivo = getCaminhoArquivo(nomeArquivo);
		URL url = amazonS3.getUrl(properties.getS3().getBuckets(), caminhoArquivo);

		return FotoRecuperada.builder().url(url.toString()).build();
	}

	@Override
	public void armazenar(NovaFoto novaFoto) {
		try {
			String caminhoArquivo = getCaminhoArquivo(novaFoto.getNomeArquivo());
			var objMetadata = new ObjectMetadata();
			objMetadata.setContentType(novaFoto.getContentType());

			var putObject = new PutObjectRequest(properties.getS3().getBuckets(), caminhoArquivo,
					novaFoto.getInputStream(), objMetadata).withCannedAcl(CannedAccessControlList.PublicRead);

			amazonS3.putObject(putObject);

		} catch (Exception e) {
			throw new StorageException("Não foi possível enviar arquivo para Amazon S3!", e);
		}
	}

	private String getCaminhoArquivo(String nomeArquivo) {
		return String.format("%s/%s", properties.getS3().getDiretorioFotos(), nomeArquivo);
	}

	@Override
	public void remover(String nomeArquivo) {
		try {
			String caminhoArquivo = getCaminhoArquivo(nomeArquivo);
			var deleteObject = new DeleteObjectRequest(properties.getS3().getBuckets(), caminhoArquivo);

			amazonS3.deleteObject(deleteObject);

		} catch (Exception e) {
			throw new StorageException("Não foi possível excluir arquivo para Amazon S3!", e);
		}
	}

}
