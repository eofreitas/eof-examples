package br.com.eof.examples.springmvc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.eof.examples.springmvc.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	List<User> findByName(String name);
}
