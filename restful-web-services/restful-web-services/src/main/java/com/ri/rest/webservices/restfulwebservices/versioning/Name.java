package com.ri.rest.webservices.restfulwebservices.versioning;

public class Name {

	private String firstname;
	private String lastname;
	public Name(String firstname, String lastname) {
		this.firstname = firstname;
		this.lastname = lastname;
	}
	public String getFirstname() {
		return firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public String toString() {
		return "Name [firstname=" + firstname + ", lastname=" + lastname + "]";
	}
	
	
	
}
