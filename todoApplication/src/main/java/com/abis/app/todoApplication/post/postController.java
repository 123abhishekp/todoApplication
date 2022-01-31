package com.abis.app.todoApplication.post;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import javax.validation.Valid;
import javax.websocket.OnMessage;

import org.hibernate.EntityMode;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.abis.app.todoApplication.exception.UserNotFoundException;
import com.abis.app.todoApplication.post.Post;
import com.abis.app.todoApplication.user.User;
import com.abis.app.todoApplication.user.UserDaoService;
import com.abis.app.todoApplication.user.UserRepository;

@RestController
public class postController {

	@Autowired
	UserDaoService daoService;

	@Autowired
	UserRepository userRepository;

	@Autowired
	PostRepository postRepository;
	
	@GetMapping("/jpa/users/{id}/posts")
	public List<Post> retriveAllUserposts(@PathVariable int id) {
		Optional<User> user = userRepository.findById(id);

		if (!user.isPresent())
			throw new UserNotFoundException("id-" + id);


		return user.get().getPosts();
	}
	
	
	// ------------Code using JPA Repository-----------------------------

	/*
	 * @GetMapping(path = "/jpa/allUsers") public List<User> getAllUserRepository()
	 * {
	 * 
	 * return userRepository.findAll(); }
	 */

	/*
	 * @GetMapping("/jpa/users/{id}") public EntityModel<User>
	 * retrieveUser(@PathVariable int id) { Optional<User> user =
	 * userRepository.findById(id);
	 * 
	 * if (!user.isPresent()) throw new UserNotFoundException("id-" + id);
	 * 
	 * EntityModel<User> resource = EntityModel.of(user.get());// new
	 * EntityModel<User>(user.get());
	 * 
	 * WebMvcLinkBuilder linkTo =
	 * linkTo(methodOn(this.getClass()).getAllUserRepository());
	 * 
	 * resource.add(linkTo.withRel("all-users"));
	 * 
	 * return resource; }
	 */

	/*
	 * @PostMapping(path = "/jpa/saveUser") public ResponseEntity<Object>
	 * createUserJpa(@Valid @RequestBody User user) {
	 * 
	 * User savedUser = userRepository.saveAndFlush(user);
	 * 
	 * URI location =
	 * ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand
	 * (savedUser.getId()) .toUri(); return
	 * ResponseEntity.created(location).build();
	 * 
	 * }
	 */
	
	
	@PostMapping(path = "/jpa/users/{id}/posts")
	public ResponseEntity<Object> createPost(@PathVariable Integer id,  @RequestBody Post post) {

		Optional<User> user = userRepository.findById(id);

		if (!user.isPresent()) 
			throw new UserNotFoundException("id-" + id);
		
		post.setUser(user.get());
		postRepository.save(post);
				
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(post.getId())
				.toUri();
		return ResponseEntity.created(location).build();

	}
	
	

	/*
	 * @DeleteMapping(path = "/jpa/deleteUsers/{id}") public void
	 * deleteJPAUser(@PathVariable int id) { userRepository.deleteById(id); ; }
	 */

	

	
	
	
}
