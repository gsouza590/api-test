package br.com.gabriel.api_test.services.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gabriel.api_test.models.User;
import br.com.gabriel.api_test.repositories.UserRepository;
import br.com.gabriel.api_test.services.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	UserRepository repository;
	
	@Override
	public User findById(Integer id) {
		return repository.findById(id).orElse(null);
	}

}
