package com.ri.spring_security_2.resources;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.security.RolesAllowed;


record Todo(String username, String description) {}

@RestController
public class TodoResource {
   private Logger logger = LoggerFactory.getLogger(getClass());

	private static List<Todo> TodosList = List.of(new Todo("sanjay", "java"), new Todo("sunay","SpringBoot"));
	
	@GetMapping("/todos")
	public List<Todo> retrieveAllTodos() {
		return TodosList;
}
	
	@GetMapping("/users/{username}/todos")
	@PreAuthorize("hasRole('USER')and #username==authentication.name") // to authorize particular resource
	@PostAuthorize("returnObject.username == 'sanjay'")
	@RolesAllowed({"ADMIN","USER"})
	@Secured({"ADMIN","USER"})
	public Todo retrieveTodoByUsername(@PathVariable String username) {
		return TodosList.get(0);
	}
	
	@PostMapping("users/{username}/todos")
	public void createTodoByUsername(@PathVariable String username, @RequestBody Todo todo) {
		logger.info("Creating {} for the username {}",todo,username);
	}
	
}
