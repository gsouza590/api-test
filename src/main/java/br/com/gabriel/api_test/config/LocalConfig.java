package br.com.gabriel.api_test.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.com.gabriel.api_test.models.User;
import br.com.gabriel.api_test.repositories.UserRepository;

@Configuration
@Profile("local")
public class LocalConfig {

	@Autowired
	private UserRepository repository;
	
	@Bean
	public List<User> startDb() {
		User u1= new User(null, "teste1", "teste@gmail.com", "123");
		User u2= new User(null, "teste2", "teste2@gmail.com", "123");
		User u3= new User(null, "teste3", "teste3@gmail.com", "123");
		return repository.saveAll(List.of(u1,u2,u3));
	}
	
}
