package br.com.gabriel.api_test.services;

import java.util.List;

import br.com.gabriel.api_test.models.User;
import br.com.gabriel.api_test.models.dto.UserDto;


public interface UserService {
User findById(Integer id);
List<User> findAll();
User create(UserDto dto);
User update(UserDto dto);
void delete(Integer id);
}
