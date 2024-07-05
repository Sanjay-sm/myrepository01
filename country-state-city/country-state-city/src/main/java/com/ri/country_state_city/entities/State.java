package com.ri.country_state_city.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity(name = "states")
public class State {
@Id
@GeneratedValue
private int id;
private String state_name;

@ManyToOne
@JoinColumn(name = "country_id")
private Country country;

public State(int id, String state_name) {
	super();
	this.id = id;
	this.state_name = state_name;
}
 
public State() {
	super();
}

public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getState_name() {
	return state_name;
}
public void setState_name(String state_name) {
	this.state_name = state_name;
}

public Country getCountry() {
	return country;
}


public void setCountry(Country country) {
	this.country = country;
}


@Override
public String toString() {
	return "State [id=" + id + ", state_name=" + state_name + "]";
}




}
