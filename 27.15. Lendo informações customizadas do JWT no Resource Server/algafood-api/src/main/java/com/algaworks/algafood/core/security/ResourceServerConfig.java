package com.algaworks.algafood.core.security;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class ResourceServerConfig {

	@Bean
	public SecurityFilterChain resourceSecurityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/oauth2/**").authenticated()
		.and()
		.csrf().disable()
		.cors().and()
		.oauth2ResourceServer().jwt().jwtAuthenticationConverter(jwtAuthenticationConverter());

		return http.formLogin(Customizer.withDefaults()).build();
	}

	private JwtAuthenticationConverter jwtAuthenticationConverter() {
	    JwtAuthenticationConverter converter = new JwtAuthenticationConverter();

	    converter.setJwtGrantedAuthoritiesConverter(jwt -> {
	        JwtGrantedAuthoritiesConverter authoritiesConverter = new JwtGrantedAuthoritiesConverter();
	        //Ler permissões vindas dos scopes
	        Collection<GrantedAuthority> grantedAuthorities  = authoritiesConverter.convert(jwt);

	        //"authorities" é um campo costumizado no AuthorizationServerConfig
	        List<String> authorities = jwt.getClaimAsStringList("authorities");

	        if (authorities == null) {
	            return grantedAuthorities; //Retorna lista com os scopes, para o fluxo de client credentials
	        }

	        grantedAuthorities.addAll(authorities
	                .stream()
	                .map(SimpleGrantedAuthority::new)
	                .collect(Collectors.toList()));

	        return grantedAuthorities;
	    });

	    return converter;
	}
}
