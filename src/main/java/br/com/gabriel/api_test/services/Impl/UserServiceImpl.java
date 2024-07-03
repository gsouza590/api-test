package br.com.gabriel.api_test.services.Impl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gabriel.api_test.models.User;
import br.com.gabriel.api_test.models.dto.UserDto;
import br.com.gabriel.api_test.repositories.UserRepository;
import br.com.gabriel.api_test.services.UserService;
import br.com.gabriel.api_test.services.exceptions.DataIntegratedViolationException;
import br.com.gabriel.api_test.services.exceptions.ObjectNotFoundException;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository repository;
	@Autowired
	ModelMapper mapper;

	@Override
	public User findById(Integer id) {
		return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
	}

	@Override
	public List<User> findAll() {
		return repository.findAll();
	}

	@Override
	public User create(UserDto dto) {
		findByEmail(dto);
		return repository.save(mapper.map(dto, User.class));
	}

	@Override
	public User update(UserDto dto) {
		findByEmail(dto);
		return repository.save(mapper.map(dto, User.class));
	}
	

	@Override
	public void delete(Integer id) {
		findById(id);
		repository.deleteById(id);
	}		
	

	private void findByEmail(UserDto dto) {
		Optional<User> user = repository.findByEmail(dto.getEmail());
		if (user.isPresent()&& !user.get().getId().equals(dto.getId())) {
			throw new DataIntegratedViolationException("Email já cadastrado no sistema");
		}
	
}
}