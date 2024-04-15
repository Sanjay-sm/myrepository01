package com.ri.rest.webservices.restfulwebservices.user;

import java.net.URI;
import java.util.List;
import java.util.Optional;

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

@RestController
public class UserJpaResource 
{
	private UserJpaRepository repository;
	private UserDaoService service;
	private PostRepository postRepository;
     
	public UserJpaResource(UserJpaRepository repository, UserDaoService service,
			PostRepository postRepository)
	{
		this.repository = repository;
		this.service = service;
		this.postRepository = postRepository;
	}
	
	@GetMapping("/users")
	public List<User> retrieveAllUsers()
	{
		return repository.findAll(); 
	}
	
	@GetMapping("/users/{id}")
	public EntityModel<User> retrieveUser(@PathVariable int id)
	{
	       Optional<User> user =  repository.findById(id); 
	       if(user.isEmpty()) 
	       {
	    	   throw new UserNotFoundException("id:"+id);
	       }
	       EntityModel<User> entityModel = EntityModel.of(user.get());
	       WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAllUsers());
	       entityModel.add(link.withRel("all-users"));
	       return entityModel;
	}


	@DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable int id)
	{
	        repository.deleteById(id); 
	       
	}
	
	@PostMapping("/users")
	public User createUser( @RequestBody User user) 
	{    
		user.setId(null);
	    return repository.save(user);
//		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
//				.path("/{id}")
//				.buildAndExpand(savedUser.getId())
//				.toUri();
//		return ResponseEntity.created(location).build();
		
	}

	@PutMapping("/users/{id}")
	public User saveUser(@PathVariable int id, @RequestBody User user	)
	{
		repository.deleteById(id);
	    repository.save(user);
	    return user;
	       
	}

	@GetMapping("/jpa/users/{id}/posts")
	public List<Post> retrievePostsOfUser(@PathVariable int id)
	{
		Optional<User> user =  repository.findById(id); 
	       if(user.isEmpty()) 
	       {
	    	   throw new UserNotFoundException("id:"+id);
	       }
	       
	       return user.get().getPosts();    
	}
	

	@PostMapping("/jpa/users/{id}/posts")
	public ResponseEntity<Post> createPostsOfUser(@PathVariable int id,@Valid @RequestBody Post post)
	{
		Optional<User> user =  repository.findById(id); 
		
	       if(user.isEmpty()) 
	       {
	    	   throw new UserNotFoundException("id:"+id);
	       }
	       
	      post.setUser(user.get());
	      Post savedPost =  postRepository.save(post);
	      URI location = ServletUriComponentsBuilder.fromCurrentRequest()
					.path("/{id}")
					.buildAndExpand(savedPost.getId())
					.toUri();
	      return ResponseEntity.created(location).build();   
	}
	
}
