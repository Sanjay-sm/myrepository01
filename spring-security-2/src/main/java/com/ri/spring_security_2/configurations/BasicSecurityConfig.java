package com.ri.spring_security_2.configurations;

import static org.springframework.security.config.Customizer.withDefaults;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableMethodSecurity(jsr250Enabled = true, securedEnabled = true) // method Security
public class BasicSecurityConfig {

	@SuppressWarnings("removal")
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((requests) -> requests
				                                         .requestMatchers("/users").hasRole("USER")
				                                         .requestMatchers("/admin?**").hasRole("ADMIN")
				                                         .anyRequest()
				                                         .authenticated());
		http.sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		// http.formLogin(withDefaults());
		http.httpBasic(withDefaults());
		http.csrf().disable(); // disabling CSRF for a stateless session, if we use basic authorization instead
								// of formLogin
		http.headers().frameOptions(withDefaults()).disable();
		return http.build();
	}

	// Global CORS Configuration
	@Bean
	public WebMvcConfigurer corsConfigurer() {

		return new WebMvcConfigurer() {

			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedMethods("*").allowedOrigins("url");
			}
		};
	}

// In memory user details without database
//	@Bean
//	public UserDetailsService userDetailsService() {
//		
//		UserDetails user = User.withUsername("user").password("{noop}dummy").roles("USER").build();
//		UserDetails admin = User.withUsername("admin").password("{noop}dummy").roles("ADMIN").build();
//		return new  InMemoryUserDetailsManager(user,admin);
//	}

	// creating datasource to store user details
	@Bean
	public DataSource dataSource() {
		return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
				.addScript(JdbcDaoImpl.DEFAULT_USER_SCHEMA_DDL_LOCATION).build();
	}

	// storing user details in database like h2 by Jdbc
	@Bean
	public UserDetailsService userDetailsService(DataSource dataSource) {

		UserDetails user = User.withUsername("sanjay")
				// password("{noop}dummy")
				.password("dummy").passwordEncoder(input -> passwordEncoder().encode(input)).roles("USER").build();
		
		UserDetails admin = User.withUsername("admin")
				// .password("{noop}dummy")
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

}
