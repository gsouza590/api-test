package br.com.gabriel.api_test.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.gabriel.api_test.models.User;
import br.com.gabriel.api_test.models.dto.UserDto;
import br.com.gabriel.api_test.repositories.UserRepository;
import br.com.gabriel.api_test.services.Impl.UserServiceImpl;
import br.com.gabriel.api_test.services.exceptions.DataIntegratedViolationException;
import br.com.gabriel.api_test.services.exceptions.ObjectNotFoundException;

@SpringBootTest
public class UserServiceImplTest {
	private static final Integer ID = 1;
	private static final String NAME = "Gabriel";
	private static final String EMAIL = "gabriel@gmail.com";
	private static final String PASSWORD = "123";

	@InjectMocks
	private UserServiceImpl service;

	@Mock
	private UserRepository repository;

	@Mock
	private ModelMapper mapper;

	private User user;
	private UserDto dto;
	private Optional<User> optionalUser;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		startUser();
	}

	@Test
    void whenFindByIdThenReturnAnUserInstance() {
        when(repository.findById(Mockito.anyInt())).thenReturn(optionalUser);

        User response = service.findById(ID);
        assertNotNull(response);
        assertEquals(User.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getName());
        assertEquals(EMAIL, response.getEmail());
        assertEquals(PASSWORD, response.getPassword());
        
    }

	@Test
    void whenFindByIdThenReturnAnObjectNotFoundException() {
		when(repository.findById(anyInt())).thenThrow(new ObjectNotFoundException("Objeto não Encontrado"));
		
		try {
			service.findById(ID);
		}catch(Exception ex) {
			assertEquals(ObjectNotFoundException.class, ex.getClass());
			assertEquals("Objeto não Encontrado", ex.getMessage());
		}
	}

	@Test
	void whenFindAllThenReturnAnListOfUsers() {
		
		when(repository.findAll()).thenReturn(List.of(user));
		
		List<User> response = service.findAll();
		assertNotNull(response);
		assertEquals(1,response.size());
		assertEquals(User.class,response.get(0).getClass());
		assertEquals(ID,response.get(0).getId());
		assertEquals(NAME,response.get(0).getName());
		assertEquals(EMAIL,response.get(0).getEmail());
		assertEquals(PASSWORD,response.get(0).getPassword());
		
	}

	@Test
	void whenCreateThenReturnSuccesss() {
		when(repository.save(any())).thenReturn(user);

		User response = service.create(dto);
		
		assertNotNull(response);
		assertEquals(User.class,response.getClass());
		assertEquals(ID,response.getId());
		assertEquals(NAME,response.getName());
		assertEquals(EMAIL,response.getEmail());
		assertEquals(PASSWORD,response.getPassword());
	}

	@Test
	void whenCreateThenReturnAnIntegrityViolationException() {
		when(repository.findByEmail(anyString())).thenReturn(optionalUser);

		try {
			service.create(dto);

		} catch (Exception e) {
			assertEquals(DataIntegratedViolationException.class,e.getClass());
			assertEquals("Email já cadastrado no sistema",e.getMessage());
		}
	}

	@Test
	void whenUpdateThenReturnSuccesss() {
		when(repository.save(any())).thenReturn(user);

		User response = service.update(dto);
		
		assertNotNull(response);
		assertEquals(User.class,response.getClass());
		assertEquals(ID,response.getId());
		assertEquals(NAME,response.getName());
		assertEquals(EMAIL,response.getEmail());
		assertEquals(PASSWORD,response.getPassword());
	}

	@Test
	void whenUpdateThenReturnAnIntegrityViolationException() {
		when(repository.findByEmail(anyString())).thenReturn(optionalUser);

		try {
			optionalUser.get().setId(2);
			service.create(dto);

		} catch (Exception e) {
			assertEquals(DataIntegratedViolationException.class,e.getClass());
			assertEquals("Email já cadastrado no sistema",e.getMessage());
		}
	}

	@Test
	void deleteWithSucess() {
		when(repository.findById(anyInt())).thenReturn(optionalUser);
		doNothing().when(repository).deleteById(anyInt());
		service.delete(ID);
		verify(repository, times(1)).deleteById(anyInt());
	}
	
	@Test
	void deleteWithObjectNotFoundException() {
		when(repository.findById(anyInt())).thenThrow(new ObjectNotFoundException("Objeto não encontrado"));
		try {
			service.delete(ID);
		}catch(Exception e) {
			assertEquals(ObjectNotFoundException.class, e.getClass());
			assertEquals("Objeto não encontrado", e.getMessage());

		}
	}

	private void startUser() {
		user = new User(ID, NAME, EMAIL, PASSWORD);
		dto = new UserDto(ID, NAME, EMAIL, PASSWORD);
		optionalUser = Optional.of(new User(ID, NAME, EMAIL, PASSWORD));

	}
}
