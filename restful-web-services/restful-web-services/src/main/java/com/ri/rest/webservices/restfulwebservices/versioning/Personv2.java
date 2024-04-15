package com.ri.rest.webservices.restfulwebservices.versioning;

public class Personv2 {

	private Name name;

	public Personv2(Name name) {
		this.name = name;
	}

	public Name getName() {
		return name;
	}

	public String toString() {
		return "Personv2 [name=" + name + "]";
	}
	
}
