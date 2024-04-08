package com.edureka.authservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class LibraryAuthServerConfiguration extends AuthorizationServerConfigurerAdapter{

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception{
		
		clients.inMemory().withClient("myclientapp").secret("secret")
		.authorizedGrantTypes("password").scopes("read","write").accessTokenValiditySeconds(90000)
		.refreshTokenValiditySeconds(100000).redirectUris("http://localhost:8087");
	}
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception{
		endpoints.accessTokenConverter(jwtAccessTokenConverter()).tokenStore(jwtTokenStore())
		.userDetailsService(userDetailsService).authenticationManager(authenticationManager);

	}
	
	@Bean
	public DefaultTokenServices tokenServicesDefault() {
		DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
		defaultTokenServices.setTokenStore(jwtTokenStore());
		return defaultTokenServices;
	}
	
	@Bean
	public JwtTokenStore jwtTokenStore() {
		return new JwtTokenStore(jwtAccessTokenConverter());
	}
	
	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter() {
		JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
		jwtAccessTokenConverter.setSigningKey("1338438349hkjfshkaekfkahafhkdahkashdgg");
		
		return jwtAccessTokenConverter;
	}
	
}
