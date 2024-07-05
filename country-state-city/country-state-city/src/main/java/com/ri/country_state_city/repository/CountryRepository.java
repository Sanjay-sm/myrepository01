package com.ri.country_state_city.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ri.country_state_city.entities.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer>{

}
