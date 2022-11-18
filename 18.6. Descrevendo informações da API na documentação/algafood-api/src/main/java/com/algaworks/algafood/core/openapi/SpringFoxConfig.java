package com.algaworks.algafood.core.openapi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SpringFoxConfig {

    @Bean
    Docket apiDocket() {
        return new Docket(DocumentationType.OAS_30)
                .select()
	                .apis(RequestHandlerSelectors.basePackage("com.algaworks.algafood.api"))
	                .paths(PathSelectors.any())
	//                .paths(PathSelectors.ant("/restaurantes/*"))
	                .build()
	            .apiInfo(apiInfo());    
    }
    
    ApiInfo apiInfo() {
    	return new ApiInfoBuilder()
    			.title("AlgaFood API")
    			.description("API aberta para clientes e restaurantes")
    			.version("1")
    			.contact(new Contact("AlgaWorks", "https://www.algaworks.com", "contato@algaworks.com"))
    			.build();
    }
}
