package com.example.spring_transaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class AppRunner implements CommandLineRunner{

	private BookingService service;
	private Logger log = LoggerFactory.getLogger(AppRunner.class);
	
	public AppRunner(BookingService service) {
		this.service = service;
	}
	
	@Override
	public void run(String... args) throws Exception {
		
		service.book("Alice", "Bob", "Carol");
		Assert.isTrue(service.findAllBookings().size()==3, 
				"First bookingm should work with no problem");
		log.info("Alice, Bob and Carol have been booked");
	    try {
	      service.book("Chris", "Samuel");
	    } catch (RuntimeException e) {
	      log.info("v--- The following exception is expect because 'Samuel' is too " +
	          "big for the DB ---v");
	      log.error(e.getMessage());
	    }
	    

	    for (String person : service.findAllBookings()) {
	      log.info("So far, " + person + " is booked.");
	    }
	    log.info("You shouldn't see Chris or Samuel. Samuel violated DB constraints, " +
	        "and Chris was rolled back in the same TX");
	    Assert.isTrue(service.findAllBookings().size() == 3,
	        "'Samuel' should have triggered a rollback");

	    try {
	      service.book("Buddy", null);
	    } catch (RuntimeException e) {
	      log.info("v--- The following exception is expect because null is not " +
	          "valid for the DB ---v");
	      log.error(e.getMessage());
	    }

	    for (String person :service.findAllBookings()) {
	      log.info("So far, " + person + " is booked.");
	    }
	    log.info("You shouldn't see Buddy or null. null violated DB constraints, and " +
	        "Buddy was rolled back in the same TX");
	    Assert.isTrue(service.findAllBookings().size() == 3,
	        "'null' should have triggered a rollback");
	}

}
