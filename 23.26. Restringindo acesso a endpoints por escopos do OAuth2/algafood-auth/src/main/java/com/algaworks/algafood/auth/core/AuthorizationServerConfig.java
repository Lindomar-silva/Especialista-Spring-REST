package com.algaworks.algafood.auth.core;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

@SuppressWarnings("deprecation")
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private PasswordEncoder passwordEncoder;
	 
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtKeyStoreProperties jwtKeyStoreProperties;
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients
			.inMemory()
				.withClient("algafood-web")
				.secret(passwordEncoder.encode("web123"))
				.authorizedGrantTypes("password", "refresh_token")
				.scopes("WRITE", "READ")
				.accessTokenValiditySeconds(60 * 60 * 6) // 6 horas
				.refreshTokenValiditySeconds(60 * 24 * 60 * 60) // 60 dias
			.and()
				.withClient("foodanalytics")
				.secret(passwordEncoder.encode(""))
				.authorizedGrantTypes("authorization_code")
				.scopes("WRITE", "READ")
				.redirectUris("http://localhost:8082")
			.and()
				.withClient("webadmin")
				.authorizedGrantTypes("implicit")
				.scopes("WRITE", "READ")
				.redirectUris("http://aplicacao-cliente")
			.and()
				.withClient("faturamento")
				.secret(passwordEncoder.encode("faturamento123"))
				.authorizedGrantTypes("client_credentials")
				.scopes("WRITE", "READ")
			.and()
				.withClient("checktoken")
					.secret(passwordEncoder.encode("check123"));
	}
	
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
//		security.checkTokenAccess("isAuthenticated()");
		security
			.checkTokenAccess("permitAll()")
			.tokenKeyAccess("permitAll()")
			.allowFormAuthenticationForClients();
	}
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		var enhancerChain = new TokenEnhancerChain();
		enhancerChain.setTokenEnhancers(Arrays.asList(new JwtCustonClaimsTokenEnhancer(), jwtAccessTokenConverter()));
		
		endpoints
			.authenticationManager(authenticationManager)
			.userDetailsService(userDetailsService)
			.reuseRefreshTokens(false)
			.accessTokenConverter(jwtAccessTokenConverter())
			.tokenEnhancer(enhancerChain)
			.approvalStore(approvalStore(endpoints.getTokenStore()))
			.tokenGranter(tokenGranter(endpoints));
	}
	
	private ApprovalStore approvalStore(TokenStore tokenStore) {
		var tokenApprovalStore = new TokenApprovalStore();
		tokenApprovalStore.setTokenStore(tokenStore);

		return tokenApprovalStore;
	}
	
	@Bean
	 public JwtAccessTokenConverter jwtAccessTokenConverter() {
		 var jwtAccessTokenConverter = new JwtAccessTokenConverter();
		 
		 var jksResource = new ClassPathResource(jwtKeyStoreProperties.getPath());
		 var keyStorePass = jwtKeyStoreProperties.getPassword();
		 var keyPairAlias = jwtKeyStoreProperties.getKeypairAlias();
		 
		 var keyStorageFactory=  new KeyStoreKeyFactory(jksResource, keyStorePass.toCharArray());
		 var keyPair = keyStorageFactory.getKeyPair(keyPairAlias);
		 
		 jwtAccessTokenConverter.setKeyPair(keyPair);
		 
		 return jwtAccessTokenConverter;
	 }
	
	private TokenGranter tokenGranter(AuthorizationServerEndpointsConfigurer endpoints) {
		var pkceAuthorizationCodeTokenGranter = new PkceAuthorizationCodeTokenGranter(endpoints.getTokenServices(),
				endpoints.getAuthorizationCodeServices(), endpoints.getClientDetailsService(),
				endpoints.getOAuth2RequestFactory());
		
		var granters = Arrays.asList(
				pkceAuthorizationCodeTokenGranter, endpoints.getTokenGranter());
		
		return new CompositeTokenGranter(granters);
	}
}
