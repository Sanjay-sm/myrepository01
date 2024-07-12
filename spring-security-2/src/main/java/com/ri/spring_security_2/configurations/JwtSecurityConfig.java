package com.ri.spring_security_2.configurations;

import static org.springframework.security.config.Customizer.withDefaults;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPublicKey;
import java.util.List;
import java.util.UUID;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.KeySourceException;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSelector;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

//@Configuration
public class JwtSecurityConfig {

	@SuppressWarnings("removal")
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((requests) -> requests.anyRequest().authenticated());

		http.sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		// http.formLogin(withDefaults());
		http.httpBasic(withDefaults());
		http.csrf(csrf -> csrf.disable());
		http.headers(headers -> headers.frameOptions(frameOptionsConfig -> frameOptionsConfig.disable()));
		http.oauth2ResourceServer(oAuth2 -> oAuth2.jwt(withDefaults()));
		return http.build();
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {

		return new WebMvcConfigurer() {

			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedMethods("*").allowedOrigins("url");
			}
		};
	}

	@Bean
	public DataSource dataSource() {
		return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
				.addScript(JdbcDaoImpl.DEFAULT_USER_SCHEMA_DDL_LOCATION).build();
	}

	@Bean
	public UserDetailsService userDetailsService(DataSource dataSource) {

		UserDetails user = User.withUsername("user")
				.password("dummy").passwordEncoder(input -> passwordEncoder().encode(input)).roles("USER").build();

		UserDetails admin = User.withUsername("admin")
				.password("dummy").passwordEncoder(input -> passwordEncoder().encode(input)).roles("ADMIN").build();

		JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
		jdbcUserDetailsManager.createUser(user);
		jdbcUserDetailsManager.createUser(admin);
		return jdbcUserDetailsManager;
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public KeyPair keyPair() {
		try {
			KeyPairGenerator instance = KeyPairGenerator.getInstance("RSA");
			instance.initialize(2048);
			return instance.generateKeyPair();
		   }  
		catch (Exception e) {
			throw new RuntimeException(e);		
		}
	}
	
	@Bean
	public RSAKey rsaKey(KeyPair keyPair) {
		return new RSAKey.Builder((RSAPublicKey)keyPair.getPublic())
				.privateKey(keyPair.getPrivate())
				.keyID(UUID.randomUUID().toString())
				.build();
	}
	
	@Bean
	public JWKSource<SecurityContext> jwkResource(RSAKey rsaKey){
		JWKSet jwkSet = new JWKSet(rsaKey);	
		return (jwkSelector,context) -> jwkSelector.select(jwkSet);
	}
	
	@Bean
	public JwtDecoder jwtDecoder(RSAKey rsaKey) {
		try {
			return NimbusJwtDecoder.withPublicKey(rsaKey.toRSAPublicKey()).build();
		} catch (Exception e) {
			
			throw new RuntimeException(e);
		}
	}

	@Bean
	public JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource) {
		return new NimbusJwtEncoder(jwkSource);
	}
	

}
