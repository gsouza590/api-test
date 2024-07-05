package br.com.gabriel.api_test.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.gabriel.api_test.models.User;
import br.com.gabriel.api_test.models.dto.UserDto;
import br.com.gabriel.api_test.services.Impl.UserServiceImpl;

@SpringBootTest
public class UserControllerTest {
	private static final Integer ID = 1;
	private static final String NAME = "Gabriel";
	private static final String EMAIL = "gabriel@gmail.com";
	private static final String PASSWORD = "123";
	@InjectMocks
	private UserController controller;

	@Mock
	private UserServiceImpl service;
	@Mock
	private ModelMapper mapper;

	private User user;
	private UserDto dto;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		startUser();
	}

	private void startUser() {
		user = new User(ID, NAME, EMAIL, PASSWORD);
		dto = new UserDto(ID, NAME, EMAIL, PASSWORD);

	}

	@Test
	void whenFindByIdThenReturnSuccess() {
		when(service.findById(anyInt())).thenReturn(user);
		when(mapper.map(any(), any())).thenReturn(dto);
		
		ResponseEntity<UserDto> response = controller.findById(ID);
		
		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(UserDto.class, response.getBody().getClass());
		
		  assertEquals(ID, response.getBody().getId());
	      assertEquals(NAME, response.getBody().getName());
	      assertEquals(EMAIL, response.getBody().getEmail());
	      assertEquals(PASSWORD, response.getBody().getPassword());
	}

	@Test
	void whenFindAllThenReturnAListOfUserDto() {
		when(service.findAll()).thenReturn(List.of(user));
		when(mapper.map(any(), any())).thenReturn(dto);
		
		ResponseEntity<List<UserDto>> response = controller.findAll();
		
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(ArrayList.class,response.getBody().getClass());
		assertEquals(UserDto.class,response.getBody().get(0).getClass());
		
		
		  assertEquals(ID, response.getBody().get(0).getId());
	      assertEquals(NAME, response.getBody().get(0).getName());
	      assertEquals(EMAIL, response.getBody().get(0).getEmail());
	      assertEquals(PASSWORD, response.getBody().get(0).getPassword());
	}

	@Test
	void whenCreateThenReturnCreated() {
		when(service.create(any())).thenReturn(user);
		ResponseEntity<UserDto> response= controller.create(dto);
		
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertNotNull(response.getHeaders().get("Location"));
		
	}

	@Test
	void whenUpdateThenReturnSucess() {
		when(service.update(dto)).thenReturn(user);
		when(mapper.map(any(), any())).thenReturn(dto);
		ResponseEntity<UserDto> response = controller.update(ID,dto);
		
		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(UserDto.class, response.getBody().getClass());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		

		assertEquals(ID, response.getBody().getId());
	    assertEquals(NAME, response.getBody().getName());
	    assertEquals(EMAIL, response.getBody().getEmail());
	    
	}
	
	@Test
	void whenDeleteThenReturnSuccess() {
		doNothing().when(service).delete(anyInt());
		ResponseEntity<UserDto> response = controller.delete(ID);
		
		assertNotNull(response);
		assertEquals(ResponseEntity.class, response.getClass());
		verify(service,times(1)).delete(anyInt());
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
	}
}