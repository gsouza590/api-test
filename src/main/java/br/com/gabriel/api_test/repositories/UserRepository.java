package br.com.gabriel.api_test.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gabriel.api_test.models.User;

@Repository
public interface UserRepository extends JpaRepository<User,Integer>{

	Optional<User> findByEmail(String email);

}
