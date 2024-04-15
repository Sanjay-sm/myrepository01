package com.ri.rest.webservices.restfulwebservices.versioning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VersioningPersonController 
{
	
	@GetMapping("/v1/person")
	public Personv1 getFirstvesrionOfPerson() {
		return new Personv1("bob charlie"); 	
	}
	
	@GetMapping("/v2/person")
	public Personv2 getSecondvesrionOfPerson() {
		return new Personv2(new Name("bob","charlie")); 	
	}

	@GetMapping(path = "/person",params="version1")
	public Personv1 getFirstvesrionOfPersonRerquestParam() {
		return new Personv1("bob charlie"); 	
	}

}
