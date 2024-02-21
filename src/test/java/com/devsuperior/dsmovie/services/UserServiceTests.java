package com.devsuperior.dsmovie.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.devsuperior.dsmovie.entities.UserEntity;
import com.devsuperior.dsmovie.repositories.UserRepository;
import com.devsuperior.dsmovie.tests.UserFactory;
import com.devsuperior.dsmovie.utils.CustomUserUtil;

@ExtendWith(SpringExtension.class)
@ContextConfiguration
public class UserServiceTests {

	@InjectMocks
	private UserService service;
	
	@Mock
	private UserRepository repository;
	
	@Mock
	private CustomUserUtil userUtil;
	
	private String username , userInexistente;
	
	private UserEntity userEntity;
	
	@BeforeEach
	void setup() throws Exception {
		//var
		username = "maria@gmail.com";
		userInexistente = "pedro@gmail.com";
		userEntity = UserFactory.createUserEntity();
		
		// userUtil
		Mockito.when(userUtil.getLoggedUsername()).thenReturn(username);
		
		//repository
		Mockito.when(repository.findByUsername(username)).thenReturn(Optional.of(userEntity));
		Mockito.when(repository.findByUsername(userInexistente)).thenReturn(Optional.empty());

	}

	@DisplayName("autenticado deve retornar a entidade do usuário quando o usuário existe")
	@Test
	public void authenticatedShouldReturnUserEntityWhenUserExists() {
		
		 UserEntity resultado = service.authenticated();
		 
		 assertNotNull(resultado);
		 assertEquals(resultado, userEntity);
		 assertEquals(resultado.getUsername(), username);
		 assertNotNull(resultado.getPassword());
		 assertNotNull(resultado.getRoles());
	}

	@DisplayName("autenticado deve Lançar UsernameNotFoundException quando o usuário não existe")
	@Test
	public void authenticatedShouldThrowUsernameNotFoundExceptionWhenUserDoesNotExists() {
		Mockito.when(userUtil.getLoggedUsername()).thenThrow(ClassCastException.class);

		assertThrows(UsernameNotFoundException.class, () -> {
			@SuppressWarnings("unused")
			UserEntity resultado = service.authenticated();
		});

	}

	@Test
	public void loadUserByUsernameShouldReturnUserDetailsWhenUserExists() {
	}

	@Test
	public void loadUserByUsernameShouldThrowUsernameNotFoundExceptionWhenUserDoesNotExists() {
	}
}
