package br.com.gabriel.api_test.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gabriel.api_test.models.User;

@Repository
public interface UserRepository extends JpaRepository<User,Integer>{

}
