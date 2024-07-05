package com.ri.country_state_city.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ri.country_state_city.entities.City;
import com.ri.country_state_city.entities.Country;
import com.ri.country_state_city.entities.State;
import com.ri.country_state_city.repository.CityRepository;
import com.ri.country_state_city.repository.CountryRepository;
import com.ri.country_state_city.repository.StateRepository;
import com.ri.country_state_city.service.CountryService;

@RestController
public class CountryController {

	//private CountryService service;
	private CityRepository cityRepository;
	private StateRepository stateRepository;
	private CountryRepository countryRepository;

	
	
	public CountryController(CityRepository repository, StateRepository stateRepository, CountryRepository countryRepository) {
		super();
		this.cityRepository = repository;
		this.stateRepository = stateRepository; 
		this.countryRepository = countryRepository; 
	}



	@GetMapping("/city-list/{id}")
	public Object[] getAllCities(@PathVariable int id){
		Country country = countryRepository.findById(id).get();
		List<City> cities = country.getCityLists();
		List<State> states = country.getStateLists();
		Object[] response = {country,cities,states};
		for(Object object:response) {
			System.out.println(object);
		}
		return response ;
	}
	

	
	
	
	
}
