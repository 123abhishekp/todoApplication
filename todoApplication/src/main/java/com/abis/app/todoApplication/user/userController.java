package com.abis.app.todoApplication.user;

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

@RestController
public class userController {

	@Autowired
	UserDaoService daoService;

	@Autowired
	UserRepository repository;

	// ------------Code using JPA Repository-----------------------------

	@GetMapping(path = "/jpa/allUsers")
	public List<User> getAllUserRepository() {

		return repository.findAll();
	}

	@GetMapping("/jpa/users/{id}")
	public EntityModel<User> retrieveUser(@PathVariable int id) {
		Optional<User> user = repository.findById(id);

		if (!user.isPresent())
			throw new UserNotFoundException("id-" + id);

		EntityModel<User> resource = EntityModel.of(user.get());// new EntityModel<User>(user.get());

		WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getAllUserRepository());

		resource.add(linkTo.withRel("all-users"));

		return resource;
	}

	@PostMapping(path = "/jpa/saveUser")
	public ResponseEntity<Object> createUserJpa(@Valid @RequestBody User user) {

		User savedUser = repository.saveAndFlush(user);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId())
				.toUri();
		return ResponseEntity.created(location).build();

	}
	
	@DeleteMapping(path = "/jpa/deleteUsers/{id}")
	public void deleteJPAUser(@PathVariable int id) {
		repository.deleteById(id);
		;
	}
	
	
	// ------------Code using Static Repository-----------------------------

	@GetMapping(path = "/allUsers")
	public ArrayList<User> getAllUser() {

		return daoService.findAll();
	}

	@GetMapping(path = "/getUsers/{id}")
	public EntityModel<User> getUser(@PathVariable int id) {
		User user = daoService.findUser(id);

		if (user == null) {
			throw new UserNotFoundException("User Not Found for id" + id);
		}

		EntityModel<User> model = EntityModel.of(user);

		WebMvcLinkBuilder linktoUsers = linkTo(methodOn(this.getClass()).getAllUser());

		model.add(linktoUsers.withRel("all-Users"));
		return model;
	}

	@PostMapping(path = "/saveUser")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {

		User savedUser = daoService.save(user);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId())
				.toUri();
		return ResponseEntity.created(location).build();

	}

	@DeleteMapping(path = "/deleteUsers/{id}")
	public User deleteUser(@PathVariable int id) {
		User user = daoService.deleteUser(id);
		if (user == null) {
			throw new UserNotFoundException("User Not Found for id " + id);
		}
		return user;
	}

}
