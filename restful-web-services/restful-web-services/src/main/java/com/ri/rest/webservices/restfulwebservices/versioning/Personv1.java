package com.ri.rest.webservices.restfulwebservices.versioning;

public class Personv1 {
	private String name;

	public Personv1(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	public String toString() {
		return "Personv1 [name=" + name + "]";
	}
	

}
