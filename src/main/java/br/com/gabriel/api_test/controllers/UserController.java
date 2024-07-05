package br.com.gabriel.api_test.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.gabriel.api_test.models.User;
import br.com.gabriel.api_test.models.dto.UserDto;
import br.com.gabriel.api_test.services.UserService;

@RestController
@RequestMapping(value = "/user")
public class UserController {

	@Autowired
	private UserService service;
	@Autowired
	private ModelMapper mapper;

	@GetMapping
	public ResponseEntity<List<UserDto>> findAll() {
		return ResponseEntity.ok()
				.body(service.findAll().stream().map(x -> mapper.map(x, UserDto.class)).collect(Collectors.toList()));

	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<UserDto> findById(@PathVariable Integer id) {
		return ResponseEntity.ok().body(mapper.map(service.findById(id), UserDto.class));

	}

	@PostMapping
	public ResponseEntity<UserDto> create(@RequestBody UserDto dto) {
		return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(service.create(dto).getId()).toUri()).build();

	}

	@PutMapping(value="/{id}")
	public ResponseEntity<UserDto> update(@PathVariable Integer id, @RequestBody UserDto dto) {
		dto.setId(id);
		User user = service.update(dto);
		return ResponseEntity.ok().body(mapper.map(user, UserDto.class));

	}
	@DeleteMapping(value="/{id}")
	public ResponseEntity<UserDto> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.noContent().build();
		
	}
}
