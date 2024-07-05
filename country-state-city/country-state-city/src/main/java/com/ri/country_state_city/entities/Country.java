package com.ri.country_state_city.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity(name = "country")
public class Country {
 @Id
 @GeneratedValue
 private int id;
 private String country_name;
 
 @OneToMany(mappedBy = "country",  fetch = FetchType.EAGER)
 private List<State> states;
 
 @OneToMany(mappedBy = "country", fetch = FetchType.EAGER)
 private List<City> cities;
 
 
public Country(int id, String country_name) {
	super();
	this.id = id;
	this.country_name = country_name;
}
public Country() {
	super();
}
public int getCountryId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getCountry_name() {
	return country_name;
}
public void setCountry_name(String country_name) {
	this.country_name = country_name;
}

public List<State> getStateLists() {
	return states;
}
public void setStateLists(List<State> stateLists) {
	this.states = stateLists;
}
public List<City> getCityLists() {
	return cities;
}
public void setCityLists(List<City> cityLists) {
	this.cities = cityLists;
}
@Override
public String toString() {
	return "Country [id=" + id + ", country_name=" + country_name + "]";
}
 
 
}
