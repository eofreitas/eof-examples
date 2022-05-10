package br.com.eof.examples.springmvc.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.eof.examples.springmvc.controllers.exceptions.NotFoundException;
import br.com.eof.examples.springmvc.entities.User;
import br.com.eof.examples.springmvc.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	private UserService service;

	public UserController(UserService service) {
		this.service = service;
	}

	@GetMapping
	public List<User> getUsers() {
		return service.findAll();
	}

	@GetMapping("/{id}")
	public User getUser(@PathVariable Long id) throws NotFoundException {
		return service.findById(id).orElseThrow(
				() -> new NotFoundException("User id: " + id + " not found"));
	}
}
