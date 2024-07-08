package com.example.spring_transaction;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookingService {
 
	private JdbcTemplate template;
	private Logger log  = LoggerFactory.getLogger(BookingService.class);
	
	public BookingService(JdbcTemplate template) {
		this.template = template;
	}
	
	@Transactional
	public void book(String...persons) {
		for(String person : persons) {
			log.info("Booking " + person +" in a seat..");
			template.update("insert into BOOKINGS(FIRST_NAME) values(?)", person);
			
		}
	}
	
	public List<String> findAllBookings(){
		return template.query("select FIRST_NAME from BOOKINGS",
		        (rs, rowNum) -> rs.getString("FIRST_NAME"));
	}
}
