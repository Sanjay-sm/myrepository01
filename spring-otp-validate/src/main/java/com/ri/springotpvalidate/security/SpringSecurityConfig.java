package com.ri.springotpvalidate.security;

import java.net.http.HttpRequest;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.ri.springotpvalidate.exception.CustomAccessDeniedHandler;
import com.ri.springotpvalidate.repository.UserRepository;
import com.ri.springotpvalidate.service.UserService;
import com.ri.springotpvalidate.user.UserEntity;

@Configuration
@SuppressWarnings("unused")
@EnableWebSecurity
public class SpringSecurityConfig implements UserDetailsService {
	@Autowired
	private UserRepository repository;
   
	@SuppressWarnings("deprecation")
	@Bean
	public SecurityFilterChain webSecurityConfig(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()
		.requestMatchers("/","/aboutus").permitAll()  //dashboard , Aboutus page will be permit to all user 
		.requestMatchers("/admin/**").hasAnyRole("ADMIN") //Only admin user can login 
		.requestMatchers("/user/**").hasAnyRole("USER") //Only normal user can login 
		.anyRequest().authenticated() //Rest of all request need authentication 
		        .and()
		        .formLogin()
		.loginPage("/login")  //Loginform all can access .. 
		.defaultSuccessUrl("/dashboard")
		.failureUrl("/login?error")
		.permitAll()
		.and()
		        .logout()
		.permitAll()
		.and()
		.exceptionHandling().accessDeniedHandler(accessDeniedHandler());
		return http.build();

		}

	private AccessDeniedHandler accessDeniedHandler() {
		
		return new CustomAccessDeniedHandler();
		
	}
	
	@Override
	@Bean
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity user = repository.findByUsername(username);
		GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole());
		UserDetails userDetails = (UserDetails) new User(user.getUsername(),
		user.getPassword(), Arrays.asList(authority));
		
       return userDetails;
	}
	
	}

