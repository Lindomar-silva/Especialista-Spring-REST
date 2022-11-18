package com.algaworks.algafood.core.openapi;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.core.openapi.model.PageableModelOpenApi;
import com.fasterxml.classmate.TypeResolver;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RepresentationBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Response;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.json.JacksonModuleRegistrar;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@Import(BeanValidatorPluginsConfiguration.class)
public class SpringFoxConfig {

    @Bean
    Docket apiDocket() {
    	var typeResolver = new TypeResolver();
    	
        return new Docket(DocumentationType.OAS_30)
                .select()
	                .apis(RequestHandlerSelectors.basePackage("com.algaworks.algafood.api"))
	                .paths(PathSelectors.any())
	//                .paths(PathSelectors.ant("/restaurantes/*"))
	                .build()
	            .useDefaultResponseMessages(false)
	            .ignoredParameterTypes(Page.class, MappingJacksonValue.class)    
	            .globalResponses(HttpMethod.GET, globalGetResponseMessage())
	            .globalResponses(HttpMethod.POST, globalPostPutResponseMessage())
	            .globalResponses(HttpMethod.PUT, globalPostPutResponseMessage())
	            .globalResponses(HttpMethod.DELETE, globalDeleteResponseMessage())
	            .additionalModels(typeResolver.resolve(Problem.class))
	            .directModelSubstitute(Pageable.class, PageableModelOpenApi.class)
	            .apiInfo(apiInfo())
	            .tags(new Tag("Cidades", "Gerencia as cidades"))
	            .tags(new Tag("Grupos", "Gerencia os grupos de usuários"));    
    }
    
    private List<Response> globalGetResponseMessage() {
    	return Arrays.asList(
    			responseBuilder(HttpStatus.NOT_ACCEPTABLE, "Recurso não possui representação que poderia ser aceita pelo consumidor"),
    			responseBuilder(HttpStatus.INTERNAL_SERVER_ERROR, problemaModel(), "Error interno do servidor")
    		);
    }
    
    private List<Response> globalPostPutResponseMessage() {
    	return Arrays.asList(
	    		responseBuilder(HttpStatus.BAD_REQUEST, problemaModel(), "Requisição inválida (erro do cliente)"),
    			responseBuilder(HttpStatus.NOT_ACCEPTABLE, "Recurso não possui representação que poderia ser aceita pelo consumidor"),
    			responseBuilder(HttpStatus.UNSUPPORTED_MEDIA_TYPE, problemaModel(), "Requisição recusada porque o corpo está em um formato não suportado"),
    			responseBuilder(HttpStatus.INTERNAL_SERVER_ERROR, problemaModel(), "Error interno do servidor")
    		);
    }
    
	private List<Response> globalDeleteResponseMessage() {
		return Arrays.asList(
				responseBuilder(HttpStatus.BAD_REQUEST, problemaModel(), "Requisição inválida (erro do cliente)"),
				responseBuilder(HttpStatus.INTERNAL_SERVER_ERROR, problemaModel(), "Error interno do servidor")
			);
	}
  
	private Response responseBuilder(HttpStatus status, String descricao) {
		return new ResponseBuilder()
				.code(String.valueOf(status.value()))
				.description(descricao)					
				.build();
	}
	
	private Response responseBuilder(HttpStatus status, Consumer<RepresentationBuilder> problemaModel, String descricao) {
		return new ResponseBuilder()
				.code(String.valueOf(status.value()))
				.representation(MediaType.APPLICATION_JSON).apply(problemaModel)					
				.description(descricao)
				.build();
	}
	
	private Consumer<RepresentationBuilder> problemaModel() {
	    return r -> r.model(m -> m.name("Problema")
	            .referenceModel(ref -> ref.key(k -> k.qualifiedModelName(
	                    q -> q.name("Problema").namespace("com.algaworks.algafood.api.exceptionhandler")))));
	}
	
    private ApiInfo apiInfo() {
    	return new ApiInfoBuilder()
    			.title("AlgaFood API")
    			.description("API aberta para clientes e restaurantes")
    			.version("1")
    			.contact(new Contact("AlgaWorks", "https://www.algaworks.com", "contato@algaworks.com"))
    			.build();
    }

    // Resolvendo o problema de serialização de OffsetDateTime
    @Bean
    JacksonModuleRegistrar springFoxJacksonConfig() {
        return objectMapper -> objectMapper.registerModule(new JavaTimeModule());
    }
    
}
