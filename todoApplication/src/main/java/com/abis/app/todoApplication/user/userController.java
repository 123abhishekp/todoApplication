package com.abis.app.todoApplication.user;

import java.net.URI;
import java.util.ArrayList;
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

@RestController
public class userController {

	@Autowired
	UserDaoService daoService;
	


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
		
		EntityModel<User> model= EntityModel.of(user);
		
		WebMvcLinkBuilder linktoUsers= linkTo(methodOn(this.getClass()).getAllUser()); 
		
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
