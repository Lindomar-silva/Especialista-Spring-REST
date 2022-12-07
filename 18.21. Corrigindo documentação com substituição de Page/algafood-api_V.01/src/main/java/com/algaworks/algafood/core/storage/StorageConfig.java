package com.algaworks.algafood.core.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.algaworks.algafood.core.storage.StorageProperties.TipoStorage;
import com.algaworks.algafood.domain.service.FotoStorageService;
import com.algaworks.algafood.infrastructure.service.storage.LocalFotoStorageService;
import com.algaworks.algafood.infrastructure.service.storage.S3FotoStorageService;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class StorageConfig {

	@Autowired
	private StorageProperties properties;

	@Bean
	@ConditionalOnProperty(name = "algafood.storage.tipo", havingValue = "s3")
	AmazonS3 amazonS3() {
		var credentials = new BasicAWSCredentials(
				properties.getS3().getAccessKeyId(),
				properties.getS3().getSecretAccessKey());
		System.err.println("TESTE...");
		return AmazonS3ClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(credentials))
				.withRegion(properties.getS3().getRegiao())
				.build();
	}
	
	@Bean
	FotoStorageService fotoStorageService() {
		if (TipoStorage.S3.equals(properties.getTipo())) {
			return new S3FotoStorageService();
		} else {
			return new LocalFotoStorageService();
		}
	}
}
