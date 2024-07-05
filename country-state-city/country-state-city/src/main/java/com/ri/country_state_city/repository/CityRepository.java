package com.ri.country_state_city.repository;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ri.country_state_city.entities.City;
import com.ri.country_state_city.entities.Country;
import com.ri.country_state_city.entities.State;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {
@Query("SELECT c.city_name FROM cities c WHERE c.country_id = :id")	
 List<City> findAllCitiesByCountryId(@Param("id") int  id);

 
}
