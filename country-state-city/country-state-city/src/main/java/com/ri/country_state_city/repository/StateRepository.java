package com.ri.country_state_city.repository;

import java.util.List;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import com.ri.country_state_city.entities.State;

public interface StateRepository extends JpaRepository<State,Integer> {
	@Query("SELECT s.state_name FROM states s WHERE s.country_id = :id")	
	List<State> findAllStatesByCountryId(@Param("id")int id);
}
