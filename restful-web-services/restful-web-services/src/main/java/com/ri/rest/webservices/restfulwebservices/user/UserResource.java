package com.ri.rest.webservices.restfulwebservices.user;

import java.net.URI;
import java.util.List;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;

//@RestController
public class UserResource 
{

	private UserDaoService service;
     
	public UserResource(UserDaoService service) 
	{
		this.service = service;
	}
	
	@GetMapping("/users")
	public List<User> retrieveAllUsers()
	{
	
		return service.findAll(); 
	}
	
	@GetMapping("/users/{id}")
	public EntityModel<User> retrieveUser(@PathVariable int id)
	{
	       User user =  service.findOne(id); 
	       if(user == null) 
	       {
	    	   throw new UserNotFoundException("id:"+id);
	       }
	       EntityModel<User> entityModel = EntityModel.of(user);
	       WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAllUsers());
	       entityModel.add(link.withRel("all-users"));
	       return entityModel;
	}


	@DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable int id)
	{
	        service.deleteById(id); 
	       
	}
	
	@PutMapping("/users/{id}")
	public User updateUser(@PathVariable int id, @RequestBody User user	)
	{
		service.deleteById(id);
	    service.save(user,id);
	    return user;
	       
	}
	
	@PostMapping("/users")
	public ResponseEntity<User> createUser( @RequestBody User user) 
	{    
		User savedUser = service.save(user,user.getId());
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(savedUser.getId())
				.toUri();
		return ResponseEntity.created(location).build();
		
	}
	
}
