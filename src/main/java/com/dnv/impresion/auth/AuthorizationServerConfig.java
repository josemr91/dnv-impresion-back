package com.dnv.impresion.auth;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

// Configuracion del servidor de autorizacion, encargado de generar el token de acceso jwt

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter{
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	@Qualifier("authenticationManager")
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private InfoAdicionalToken infoAdicionalToken;

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {

		security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthtenticated()");
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory().withClient("angularapp")
				.secret(passwordEncoder.encode("12345"))
				.scopes("read", "write")
				.authorizedGrantTypes("password","refresh_token")
				.accessTokenValiditySeconds(3600)
				.refreshTokenValiditySeconds(3600);
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		
		TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
		tokenEnhancerChain.setTokenEnhancers(Arrays.asList(infoAdicionalToken, accessTokenConverter()));
		
		endpoints.authenticationManager(authenticationManager)
				.accessTokenConverter(accessTokenConverter())
				.tokenEnhancer(tokenEnhancerChain);
	}

	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {

		JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
		return jwtAccessTokenConverter;
	}
	
	
}
