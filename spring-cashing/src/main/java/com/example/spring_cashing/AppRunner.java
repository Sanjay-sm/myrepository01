package com.example.spring_cashing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppRunner implements CommandLineRunner {
	
	private Logger log = LoggerFactory.getLogger(AppRunner.class);
	
	private BookImplRepository repository;
	
	public AppRunner(BookImplRepository repository) {
		this.repository = repository;
	}


	@Override
	public void run(String... args) throws Exception {
		
		log.info(".... Fetching books");
	    log.info("isbn-1234 -->" + repository.getByIsbn("isbn-1234"));
	    log.info("isbn-4567 -->" + repository.getByIsbn("isbn-4567"));
	    log.info("isbn-1234 -->" + repository.getByIsbn("isbn-1234"));
	    log.info("isbn-4567 -->" + repository.getByIsbn("isbn-4567"));
	    log.info("isbn-1234 -->" + repository.getByIsbn("isbn-1234"));
	    log.info("isbn-1234 -->" + repository.getByIsbn("isbn-1234"));
	}

}
