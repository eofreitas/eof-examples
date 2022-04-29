package br.com.eof.examples.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.eof.examples.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	List<User> findByName(String name);
}
