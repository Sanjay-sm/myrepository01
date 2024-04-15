package com.springphonewebapp.springphonebookapp.contacts;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;

@Entity(name = "contacts")
public class Contacts {
  @Id
  @GeneratedValue
  private int id;
  private String name;
  private String email;
  private long phno;
public Contacts(int id, String name, String email, long phno) {
	super();
	this.id = id;
	this.name = name;
	this.email = email;
	this.phno = phno;
}

public Contacts() {
	super();
}

public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public long getPhno() {
	return phno;
}
public void setPhno(long phno) {
	this.phno = phno;
}
@Override
public String toString() {
	return "Contacts [id=" + id + ", name=" + name + ", email=" + email + ", phno=" + phno + "]";
}
  
  
}
