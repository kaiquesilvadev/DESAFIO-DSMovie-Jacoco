package com.devsuperior.dsmovie.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.devsuperior.dsmovie.dto.MovieDTO;
import com.devsuperior.dsmovie.dto.ScoreDTO;
import com.devsuperior.dsmovie.entities.MovieEntity;
import com.devsuperior.dsmovie.entities.ScoreEntity;
import com.devsuperior.dsmovie.entities.UserEntity;
import com.devsuperior.dsmovie.repositories.MovieRepository;
import com.devsuperior.dsmovie.repositories.ScoreRepository;
import com.devsuperior.dsmovie.tests.MovieFactory;
import com.devsuperior.dsmovie.tests.ScoreFactory;
import com.devsuperior.dsmovie.tests.UserFactory;

@ExtendWith(SpringExtension.class)
public class ScoreServiceTests {

	@InjectMocks
	private ScoreService service;

	@Mock
	private UserService userService;

	@Mock
	private MovieRepository movieRepository;

	@Mock
	private ScoreRepository scoreRepository;
	private MovieEntity movieEntity;
	private ScoreDTO scoreDTO;
	private UserEntity userEntity;
	private ScoreEntity scoreEntity;
	
	private Long movieIdExistente;

	@BeforeEach
	void setup() throws Exception {
		scoreDTO = ScoreFactory.createScoreDTO();
		
		// var
		userEntity = UserFactory.createUserEntity();
		scoreEntity = ScoreFactory.createScoreEntity();
		movieEntity = MovieFactory.createMovieEntity();
		
		movieIdExistente = movieEntity.getId();
		
		// movieRepository
		Mockito.when(movieRepository.findById(movieIdExistente)).thenReturn(Optional.of(movieEntity));
		Mockito.when(movieRepository.findById(any())).thenReturn(Optional.empty());
		Mockito.when(movieRepository.save(any())).thenReturn(movieEntity);
		
		//scoreRepository
		Mockito.when(scoreRepository.saveAndFlush(any())).thenReturn(scoreEntity);
		
		// userService
		Mockito.when(userService.authenticated()).thenReturn(userEntity);

	}

	@DisplayName("saveScore deve retornar MovieDTO")
	@Test
	public void saveScoreShouldReturnMovieDTO() {
		
		 MovieDTO resultado = service.saveScore(scoreDTO);
		
		assertNotNull(resultado);
		assertNotNull(resultado.getCount());
		assertNotNull(resultado.getId());
		assertNotNull(resultado.getCount());
		assertNotNull(resultado.getScore());
		assertTrue(resultado.getScore() > 0);
	}

	@Test
	public void saveScoreShouldThrowResourceNotFoundExceptionWhenNonExistingMovieId() {
	}
}
