package br.com.eof.examples.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.eof.examples.entities.User;
import br.com.eof.examples.repositories.UserRepository;

@Transactional
@Service
public class UserService {

	private UserRepository repository;
	
	public UserService(UserRepository repository) {
		this.repository = repository;
	}
	
	@Transactional(readOnly = true)
	public List<User> findAll() {
		return repository.findAll();
	}
	
	@Transactional(readOnly = true)
	public List<User> findByName(String name) {
		return repository.findByName(name);
	}

	@Transactional(readOnly = true)
	public Optional<User> findById(Long id) {
		return repository.findById(id);
	}
}
