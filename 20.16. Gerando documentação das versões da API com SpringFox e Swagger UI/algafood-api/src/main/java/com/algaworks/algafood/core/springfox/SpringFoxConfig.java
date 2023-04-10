package com.algaworks.algafood.core.springfox;

import java.io.File;
import java.net.URL;
import java.net.URLStreamHandler;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.context.request.ServletWebRequest;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.v1.model.CidadeDTO;
import com.algaworks.algafood.api.v1.model.CozinhaDTO;
import com.algaworks.algafood.api.v1.model.EstadoDTO;
import com.algaworks.algafood.api.v1.model.FormaPagamentoDTO;
import com.algaworks.algafood.api.v1.model.GrupoDTO;
import com.algaworks.algafood.api.v1.model.PedidoResumoDTO;
import com.algaworks.algafood.api.v1.model.PermissaoDTO;
import com.algaworks.algafood.api.v1.model.ProdutoDTO;
import com.algaworks.algafood.api.v1.model.RestauranteBasicoDTO;
import com.algaworks.algafood.api.v1.model.UsuarioDTO;
import com.algaworks.algafood.api.v1.openapi.model.CidadesModelOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.CozinhasModelOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.EstadosModelOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.FormasPagamentoModelOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.GruposModelOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.LinksModelOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.PageableModelOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.PedidosResumoModelOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.PermissoesModelOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.ProdutosModelOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.RestaurantesBasicoModelOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.UsuariosModelOpenApi;
import com.amazonaws.auth.policy.Resource;
import com.ctc.wstx.shaded.msv_core.util.Uri;
import com.fasterxml.classmate.TypeResolver;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.lowagie.text.pdf.codec.Base64.InputStream;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RepresentationBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.schema.AlternateTypeRules;
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
    Docket apiDocketV1() {
    	var typeResolver = new TypeResolver();
    	
        return new Docket(DocumentationType.OAS_30)
        		.groupName("V1")
                .select()
	                .apis(RequestHandlerSelectors.basePackage("com.algaworks.algafood.api"))
	                .paths(PathSelectors.ant("/v1/**"))
	//                .paths(PathSelectors.ant("/restaurantes/*"))
	                .build()
	            .useDefaultResponseMessages(false)
	            .ignoredParameterTypes(MappingJacksonValue.class, ServletWebRequest.class,
	            		URL.class, Uri.class, URLStreamHandler.class, Resource.class, File.class, InputStream.class)    
	            .globalResponses(HttpMethod.GET, globalGetResponseMessage())
	            .globalResponses(HttpMethod.POST, globalPostPutResponseMessage())
	            .globalResponses(HttpMethod.PUT, globalPostPutResponseMessage())
	            .globalResponses(HttpMethod.DELETE, globalDeleteResponseMessage())
	            .additionalModels(typeResolver.resolve(Problem.class))
	            .directModelSubstitute(Pageable.class, PageableModelOpenApi.class)
	            .directModelSubstitute(Links.class, LinksModelOpenApi.class)
	            .alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(PagedModel.class, CozinhaDTO.class),
						CozinhasModelOpenApi.class))
	            .alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(CollectionModel.class, CidadeDTO.class),
						CidadesModelOpenApi.class))
	            .alternateTypeRules(AlternateTypeRules.newRule(
	            		typeResolver.resolve(CollectionModel.class, EstadoDTO.class),
	            		EstadosModelOpenApi.class))
	            .alternateTypeRules(AlternateTypeRules.newRule(
	            		typeResolver.resolve(CollectionModel.class, FormaPagamentoDTO.class),
	            		FormasPagamentoModelOpenApi.class))
	            .alternateTypeRules(AlternateTypeRules.newRule(
	            	    typeResolver.resolve(CollectionModel.class, GrupoDTO.class),
	            	    GruposModelOpenApi.class))
	            .alternateTypeRules(AlternateTypeRules.newRule(
	                    typeResolver.resolve(CollectionModel.class, PermissaoDTO.class),
	                    PermissoesModelOpenApi.class))
	            .alternateTypeRules(AlternateTypeRules.newRule(
	            		typeResolver.resolve(PagedModel.class, PedidoResumoDTO.class),
	            		PedidosResumoModelOpenApi.class))
	            .alternateTypeRules(AlternateTypeRules.newRule(
	            		typeResolver.resolve(CollectionModel.class, ProdutoDTO.class),
	            		ProdutosModelOpenApi.class))
	            .alternateTypeRules(AlternateTypeRules.newRule(
	            		typeResolver.resolve(CollectionModel.class, RestauranteBasicoDTO.class),
	            		RestaurantesBasicoModelOpenApi.class))
	            .alternateTypeRules(AlternateTypeRules.newRule(
	            		typeResolver.resolve(CollectionModel.class, UsuarioDTO.class),
	            		UsuariosModelOpenApi.class))
	            .apiInfo(apiInfoV1())
	            .tags(new Tag("Cidades", "Gerencia as cidades"))
	            .tags(new Tag("Estados","Gerencia os estados"))
	            .tags(new Tag("Grupos", "Gerencia os grupos de usuários"))    
        		.tags(new Tag("Cozinhas", "Gerencia as cozinhas"))
        		.tags(new Tag("Pedidos","Gerencia os pedidos"))
				.tags(new Tag("Produtos", "Gerencia os produtos de restaurantes"))
        		.tags(new Tag("Restaurantes", "Gerencia os restaurantes"))
        		.tags(new Tag("Usuários", "Gerencia os usuários"))
        		.tags(new Tag("Estatísticas", "Estatísticas da Algafood"))
        		.tags(new Tag("Formas de Pagamento", "Gerencia as formas de pagamento"))
				.tags(new Tag("Permissões", "Gerencia as permissões"));
    }
    
    @Bean
    Docket apiDocketV2() {
    	var typeResolver = new TypeResolver();
    	
        return new Docket(DocumentationType.OAS_30)
        		.groupName("V2")
                .select()
	                .apis(RequestHandlerSelectors.basePackage("com.algaworks.algafood.api"))
	                .paths(PathSelectors.ant("/v2/**"))
	                .build()
	            .useDefaultResponseMessages(false)
	            .globalResponses(HttpMethod.GET, globalGetResponseMessage())
	            .globalResponses(HttpMethod.POST, globalPostPutResponseMessage())
	            .globalResponses(HttpMethod.PUT, globalPostPutResponseMessage())
	            .globalResponses(HttpMethod.DELETE, globalDeleteResponseMessage())
	            .additionalModels(typeResolver.resolve(Problem.class))
	            .ignoredParameterTypes(MappingJacksonValue.class, ServletWebRequest.class,
	            		URL.class, Uri.class, URLStreamHandler.class, Resource.class, File.class, InputStream.class)    
	            .directModelSubstitute(Pageable.class, PageableModelOpenApi.class)
	            .directModelSubstitute(Links.class, LinksModelOpenApi.class)
	            .apiInfo(apiInfoV2());
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
	
    private ApiInfo apiInfoV1() {
    	return new ApiInfoBuilder()
    			.title("AlgaFood API")
    			.description("API aberta para clientes e restaurantes")
    			.version("1")
    			.contact(new Contact("AlgaWorks", "https://www.algaworks.com", "contato@algaworks.com"))
    			.build();
    }
    
    private ApiInfo apiInfoV2() {
    	return new ApiInfoBuilder()
    			.title("AlgaFood API")
    			.description("API aberta para clientes e restaurantes")
    			.version("2")
    			.contact(new Contact("AlgaWorks", "https://www.algaworks.com", "contato@algaworks.com"))
    			.build();
    }

    // Resolvendo o problema de serialização de OffsetDateTime
    @Bean
    JacksonModuleRegistrar springFoxJacksonConfig() {
        return objectMapper -> objectMapper.registerModule(new JavaTimeModule());
    }
}
