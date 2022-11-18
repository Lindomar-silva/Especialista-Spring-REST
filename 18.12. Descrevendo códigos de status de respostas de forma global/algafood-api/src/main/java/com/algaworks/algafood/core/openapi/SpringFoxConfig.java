package com.algaworks.algafood.core.openapi;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Response;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@Import(BeanValidatorPluginsConfiguration.class)
public class SpringFoxConfig {

    @Bean
    Docket apiDocket() {
        return new Docket(DocumentationType.OAS_30)
                .select()
	                .apis(RequestHandlerSelectors.basePackage("com.algaworks.algafood.api"))
	                .paths(PathSelectors.any())
	//                .paths(PathSelectors.ant("/restaurantes/*"))
	                .build()
	            .useDefaultResponseMessages(false)
	            .globalResponses(HttpMethod.GET, globalGetResponseMessage())
	            .apiInfo(apiInfo())
	            .tags(new Tag("Cidades", "Gerencia as cidades"));    
    }
    
    private List<Response> globalGetResponseMessage() {
    	return Arrays.asList(
    			new ResponseBuilder()
					.code(httpValueString(HttpStatus.OK))
					.description("Consulta realizada com sucesso!")
					.build(),
    			new ResponseBuilder()
    				.code(httpValueString(HttpStatus.INTERNAL_SERVER_ERROR))
    				.description("Error interno do servidor")
    				.build(),
    			new ResponseBuilder()
    				.code(httpValueString(HttpStatus.NOT_ACCEPTABLE))
    				.description("Recurso não possui representação que poderia ser aceita pelo consumidor")
    				.build()
    			);
    }
    
    private ApiInfo apiInfo() {
    	return new ApiInfoBuilder()
    			.title("AlgaFood API")
    			.description("API aberta para clientes e restaurantes")
    			.version("1")
    			.contact(new Contact("AlgaWorks", "https://www.algaworks.com", "contato@algaworks.com"))
    			.build();
    }
    
	private String httpValueString(HttpStatus status) {
		return String.valueOf(status.value());
	}
}
