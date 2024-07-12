package com.ri.spring_security_2.resources;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingResource {

	
// Rest API (Resource) protecting from default spring security configuration.
	@GetMapping("/hello")
	@CrossOrigin(origins = "url") // local CORS configuration with specific origin and  by default allow from all origins 
	public String greeting() {
		return "Hello Sanjay v2";
	}
}
