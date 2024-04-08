package com.edureka.applicationgateway;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class LibraryResourceServerConfig {

	@Bean
	public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSecurity) {
		httpSecurity.authorizeExchange().anyExchange().authenticated().and().oauth2ResourceServer().jwt();

		return httpSecurity.build();
	}

	@Bean
	public ReactiveJwtDecoder reactiveJwtDecoder() {
		
		byte[] bytes = "1338438349hkjfshkaekfkahafhkdahkashdgg".getBytes();
		SecretKeySpec secretKeySpec = new SecretKeySpec(bytes, 0,bytes.length,"AES");
		return NimbusReactiveJwtDecoder.withSecretKey(secretKeySpec).build();
	}
}
