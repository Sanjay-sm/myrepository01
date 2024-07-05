package com.ri.country_state_city.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity(name = "cities")
public class City {
 @Id
 @GeneratedValue
 private int id;
 private String city_name;
 
 @ManyToOne
 @JoinColumn(name = "country_id")
 private Country country;
 
public City(int id, String city_name) {
	super();
	this.id = id;
	this.city_name = city_name;
}
public City() {
	super();
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getCity_name() {
	return city_name;
}
public void setCity_name(String city_name) {
	this.city_name = city_name;
}

public Country getCountry() {
	return country;
}
public void setCountry(Country country) {
	this.country = country;
}
@Override
public String toString() {
	return "City [id=" + id + ", city_name=" + city_name + "]";
}
 
}
