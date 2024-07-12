package com.ri.spring_security_2.resources;

import java.time.Instant;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

record JwtResponse(String token) {}

//@RestController
public class JwtAuthResource {
	
	@Autowired
	private JwtEncoder jwtEncoder;
	

	@PostMapping("/authenticate")
	public JwtResponse authenticate(Authentication authentication) {
		 return new JwtResponse(createToken(authentication));
	}

	private String createToken(Authentication authentication) {
		JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
		            .issuer("self")
		            .issuedAt(Instant.now())
		            .expiresAt(Instant.now().plusSeconds(60 * 30))
		            .subject(authentication.getName())
		            .claim("scope", createScope(authentication))
		            .build();
		
		return jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();
	}

	private Object createScope(Authentication authentication) {
		return authentication.getAuthorities()
		               .stream()
		               .map(a -> a.getAuthority())
		               .collect(Collectors.joining(" "));
	}
}
