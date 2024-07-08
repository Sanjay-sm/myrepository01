package com.example.spring_cashing;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

@Repository
public class BookImplRepository implements BookRepository {

	
	@Cacheable("books")
	public Book getByIsbn(String isbn) {
		simulateSlowService();
		return new Book(isbn, "Some Book");
	}
	
	private void simulateSlowService() {
		try 
		{
			Thread.sleep(3000L);
		} 
		catch (InterruptedException e) 
		{
			
			e.printStackTrace();
		}
	}

}
