package com.ri.springboot.myfirstwebapp.todo;

import java.util.function.Function;
import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfiguration {
	
@Bean	
 public InMemoryUserDetailsManager createUserDetails() 
 {
	 Function <String, String> passwordEncoder = 
			 input -> passwordEncoder().encode(input);
			 
	UserDetails userdetails = 
			org.springframework.security.core.userdetails
			.User.builder()
			.passwordEncoder(passwordEncoder)
			.username("tap123").password("sanjay1").roles("USER","ADMIN").build();
	return new InMemoryUserDetailsManager(userdetails);
			              
 }
 @Bean
 public PasswordEncoder passwordEncoder() {
	 return new BCryptPasswordEncoder();
 }
	
 @Bean
 public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	 http.authorizeHttpRequests(auth -> auth.anyRequest().authenticated());
	 http.formLogin(withDefaults());
	 http.csrf().disable();
	 http.headers().frameOptions().disable();
	 return http.build();
 }
	
	
	
}
